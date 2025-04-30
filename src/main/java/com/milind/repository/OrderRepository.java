package com.milind.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.milind.modal.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
    List<Order> findByUserId(Long userId);
}
