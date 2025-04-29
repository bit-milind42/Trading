package com.milind.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.milind.modal.VerificationCode;

public interface  VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    
    public VerificationCode findByUserId(Long userId);
}
