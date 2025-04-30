package com.milind.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.milind.modal.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{
    
}
