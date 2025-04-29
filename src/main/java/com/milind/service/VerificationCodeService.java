package com.milind.service;

import com.milind.domain.VerificationType;
import com.milind.modal.User;
import com.milind.modal.VerificationCode;

public interface VerificationCodeService {
    
    VerificationCode sendVerificationCode(User user,VerificationType verificationType);

    VerificationCode getVerificationCodeById(Long Id);

    VerificationCode getVerificationCodeByUser (Long userId); 

    void deleteVerificationCodeById(VerificationCode verificationCode); 

}
