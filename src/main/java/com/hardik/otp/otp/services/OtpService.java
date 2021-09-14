package com.hardik.otp.otp.services;

import com.hardik.otp.otp.model.Otp;
import com.hardik.otp.otp.repo.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OtpService {

    @Autowired
    @Qualifier("in-memory")
    OtpRepository otpRepository;

    @Autowired
    private JavaMailSender emailSender;

    public String generateOtp() {
        RestTemplate restTemplate = new RestTemplate();
        String idServiceUri = "http://localhost:4001/get"; // Replace with actual address
        String id = restTemplate.getForObject(idServiceUri, String.class);
        if (id == null || id.length() == 0) {
            return "-1";
        }
        Otp otp = new Otp(id);
        otpRepository.setOtp(otp);
        try {
            sendEmail(otp);
            return otp.getId();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    // In Production, instead of using sendEmail as method,
    //      we should have completely different microservice
    // We should pass Otp information to Apache Kafka/AWS SQS, and then email service should
    //      read from queue, so in that way, this service wont have to slow down for sending actual
    //      mail
    void sendEmail(Otp otp) throws Exception {
        String text = "Otp: " + otp.getOtpValue();
        String from = ""; // Add From Email Address
        String to = "";  // Add To Email Address

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject("Otp Verification");
        message.setText(text);
        emailSender.send(message);
    }

    public boolean validateOtp(String id, String value) {
        if (id.isEmpty() || value.isEmpty()) {
            return false;
        }

        if (value.length() != 6) {
            return false;
        }

        Otp otp = otpRepository.getOtp(id);
        if (otp == null) {
            return false;
        }

        boolean result = otp.validate(value);
        if (result) {
            otpRepository.remove(otp.getId());
        }

        return result;
    }

}
