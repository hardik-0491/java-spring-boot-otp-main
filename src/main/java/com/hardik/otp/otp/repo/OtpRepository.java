package com.hardik.otp.otp.repo;

import com.hardik.otp.otp.model.Otp;

public interface OtpRepository {
    public void setOtp(Otp otp);
    public Otp getOtp(String id);
    public void remove(String id);
}
