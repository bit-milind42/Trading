package com.milind.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.milind.modal.Withdrawal;

public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> {
    List<Withdrawal> findByUser(Long userId);
    
}
