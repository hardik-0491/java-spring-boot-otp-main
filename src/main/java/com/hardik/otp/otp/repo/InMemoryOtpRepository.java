package com.hardik.otp.otp.repo;

import com.hardik.otp.otp.model.Otp;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository("in-memory")
public class InMemoryOtpRepository implements OtpRepository {

    HashMap<String, Otp> otpRepo;

    public InMemoryOtpRepository() {
        otpRepo = new HashMap<>();
    }

    @Override
    public void setOtp(Otp otp) {
        otpRepo.put(otp.getId(), otp);
    }

    @Override
    public Otp getOtp(String id) {
        return otpRepo.get(id);
    }

    @Override
    public void remove(String id) {
        otpRepo.remove(id);
    }
}
