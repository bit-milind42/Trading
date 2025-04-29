package com.milind.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.milind.domain.VerificationType;
import com.milind.modal.User;
import com.milind.modal.VerificationCode;
import com.milind.repository.VerificationCodeRepository;
import com.milind.utils.OtpUtils;

@Service
public class VerificationCodeServiceImpl implements  VerificationCodeService{
    
    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Override
    public VerificationCode sendVerificationCode(User user,VerificationType verificationType) {
        VerificationCode verificationCode1=new VerificationCode(); 
        verificationCode1.setOtp(OtpUtils.generateOTP()); 
        verificationCode1.setVerificationType (verificationType); 
        verificationCode1.setUser(user);

        return verificationCodeRepository.save(verificationCode1);
    }

    @Override
    public VerificationCode getVerificationCodeById(Long Id) {
        Optional<VerificationCode> verificationCode= 
        verificationCodeRepository.findById(id); 
        if(verificationCode.isPresent()){ 
        return verificationCode.get(); 
        } 
        throw new Exception("verification code not found");
    }

    @Override
    public VerificationCode getVerificationCodeByUser(Long userId) {
        return verificationCodeRepository.findByUserId(userId);
    }

    @Override
    public void deleteVerificationCodeById(VerificationCode verificationCode) {
        verificationCodeRepository.delete(verificationCode);
    }
    
}
