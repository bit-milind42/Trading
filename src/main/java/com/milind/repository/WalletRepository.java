package com.milind.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.milind.modal.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long>{
    Wallet findByUserId(Long userId);
}
