package com.milind.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.milind.modal.PaymentDetails;

public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Long> {
    PaymentDetails findByUser(Long userId);
}
