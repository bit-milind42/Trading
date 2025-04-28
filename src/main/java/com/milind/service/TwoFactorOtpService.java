package com.milind.service;

import com.milind.modal.TwoFactorOTP;
import com.milind.modal.User;

public interface TwoFactorOtpService {

    TwoFactorOTP createTwoFactorOtp(User user, String otp, String jwt); 

    TwoFactorOTP FindByUser (Long userId); 

    TwoFactorOTP FindById(String id); 
    
    boolean verifyTwoFactorOtp (TwoFactorOTP twoFactorOtp, String otp); 
    
    void deleteTwoFactor0tp (TwoFactorOTP twoFactor0tp); 
}
