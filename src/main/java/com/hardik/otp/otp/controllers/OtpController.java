package com.hardik.otp.otp.controllers;

import com.hardik.otp.otp.model.ExternalOtpData;
import com.hardik.otp.otp.services.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OtpController {

    @Autowired
    private OtpService otpService;

    @GetMapping(value = "/generate")
    public String generateOtp() {
        return otpService.generateOtp();
    }

    @PostMapping(value = "/validate")
    public boolean validateOtp(@RequestBody ExternalOtpData otpData) {
        return otpService.validateOtp(otpData.getId(), otpData.getOtpValue());
    }

}
