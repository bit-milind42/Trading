package com.milind.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.milind.domain.VerificationType;
import com.milind.modal.ForgotPasswordToken;
import com.milind.modal.User;
import com.milind.repository.ForgotPasswordRespository;

@Service
public class ForgotPasswordImpl implements ForgotPasswordService {
    
    @Autowired
    private ForgotPasswordRespository forgotPasswordRespository;

    @Override
    public ForgotPasswordToken createToken(User user, String id, String otp, Object verificationType, String sendTo) {
        ForgotPasswordToken token=new ForgotPasswordToken(); 
        token.setUser(user); 
        token.setSendTo(sendTo); 
        token.setVerificationType(verificationType);
        token.setOtp(otp); 
        token.setId(id); 
        return forgotPasswordRepository.save(token); 
    }


    @Override
    public ForgotPasswordToken findById(String id) {
        Optional<ForgotPasswordToken> token = forgotPasswordRespository.findById(id);
        return token.orElse(null);
    }

    @Override
    public ForgotPasswordToken findByUser(Long userId) {
        return forgotPasswordRespository.findByUserId(userId);
    }

    @Override
    public void deleteToken(ForgotPasswordToken token) {
        forgotPasswordRespository.delete(token);
    }


    
}
