package com.milind.service;

import com.milind.modal.ForgotPasswordToken;
import com.milind.modal.User;

public interface ForgotPasswordService {
    ForgotPasswordToken createToken (User user,
        String id, String otp, 
        VerificationType verificationType, 
        String sendTo);

    ForgotPasswordToken findById(String id); 

    ForgotPasswordToken findByUser (Long userId); 

    void deleteToken (ForgotPasswordToken token); 

}
