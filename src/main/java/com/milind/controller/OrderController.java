package com.milind.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.milind.domain.OrderType;
import com.milind.modal.Coin;
import com.milind.modal.Order;
import com.milind.modal.User;
import com.milind.request.CreateOrderRequest;
import com.milind.service.CoinService;
import com.milind.service.OrderService;
import com.milind.service.UserService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    @AutoController
    private OrderService orderService;

    @AutoController
    private UserService userService;

    @AutoController
    private CoinService coinService;

    // @AutoController
    // private WalletTransactionService walletTransactionService;

    @PostMapping("/pay") 
    public ResponseEntity<Order> payOrderPayment( 
        @RequestHeader("Authorization") String jwt, 
        @RequestBody CreateOrderRequest req 
    ) throws Exception { 
        User user= userSerivce.findUserProfileByJwt(jwt); 
        Coin coin =coinService.findById(req.getCoinId()); 
        Order order= orderService.processOrder(coin, req.getQuantity(), req.getOrderType());
         
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById( 
            @RequestHeader("Authorization") String jwtToken, 
            @PathVariable Long orderId 
    ) throws Exception { 
        User user= userService.findUserProfileByJwt(jwtToken); 
        
        Order order = orderService.getOrderById(orderId); 
        if (order.getUser().getId().equals(user.getId())) { 
            return ResponseEntity.ok(order); 
        } else { 
            throw new Exception("Ypu don't have access");
        } 
    }

    @GetMapping() 
    public ResponseEntity<List<Order>> getAllOrdersForUser ( 
        @RequestHeader("Authorization") String jwt, 
        @RequestParam(required = false) OrderType order_type, 
        @RequestParam(required =false) String asset_symbol 
    ) throws Exception { 
        Long userId= userService.findUserProfileByJwt(jwt).getId(); 
        List<Order> userOrders = orderService.getAllOrdersOfUser (userId, order_type,asset_symbol); 

        return ResponseEntity.ok(userOrders); 
        
    }

}


