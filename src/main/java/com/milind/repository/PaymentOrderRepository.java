package com.milind.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.milind.modal.PaymentOrder;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder,Long> {
    
}
