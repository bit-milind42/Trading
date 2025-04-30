package com.milind.service;

import java.util.List;

import com.milind.domain.OrderType;
import com.milind.modal.Coin;
import com.milind.modal.Order;
import com.milind.modal.OrderItem;
import com.milind.modal.User;

public interface OrderService {
    Order createOrder (User user, OrderItem orderItem, OrderType orderType); 
 
    Order getOrderById (Long orderId); 

    List<Order> getAllOrdersOfUser(Long userId, OrderType orderType, String assetSymbol); 

    Order processOrder(Coin coin, double quantity, OrderType orderType, User user); 
}
