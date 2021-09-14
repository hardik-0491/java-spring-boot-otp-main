package com.hardik.otp.otp.model;

import lombok.Data;

import java.util.Date;
import java.util.Random;

@Data
public class Otp {

    private String id;
    private String otpValue;
    private Date timeStamp;
    private byte numberOfRetry;

    public Otp(String id) {
        this.id = id;

        Random random = new Random();
        char[] value = new char[6];
        value[0] = (char)(random.nextInt(26) + 'a');
        value[1] = (char)(random.nextInt(26) + 'a');
        value[2] = (char)(random.nextInt(26) + 'a');
        value[3] = (char)(random.nextInt(26) + 'a');
        value[4] = (char)(random.nextInt(26) + 'a');
        value[5] = (char)(random.nextInt(26) + 'a');

        otpValue = new String(value);

        timeStamp = new Date();
        numberOfRetry = 0;
    }

    public boolean validate(String value) {
        if (value.length() != 6) {
            numberOfRetry++;
            return false;
        }

        if (numberOfRetry > 3) {
            return false;
        }

        Date current = new Date();
        if (current.getTime() - timeStamp.getTime() > 3*60000) {
            return false;
        }

        if (otpValue.equals(value)) {
            return true;
        }

        numberOfRetry++;
        return false;
    }

}
