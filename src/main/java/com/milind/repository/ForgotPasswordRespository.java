package com.milind.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.milind.modal.ForgotPasswordToken;

public interface ForgotPasswordRespository extends JpaRepository<ForgotPasswordToken, String> {
    
    ForgotPasswordToken findByUserId(Long userId);
}
