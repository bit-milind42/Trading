package com.milind.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.milind.modal.TwoFactorOTP;

public interface TwoFactorOtpRepository extends JpaRepository<TwoFactorOTP, String>{
    TwoFactorOTP findByUserId(Long userId);
}
 