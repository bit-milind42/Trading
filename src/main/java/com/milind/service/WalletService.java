package com.milind.service;

import com.milind.modal.Order;
import com.milind.modal.User;
import com.milind.modal.Wallet;


public interface  WalletService {

    Wallet getUserWallet (User user);
    Wallet addBalance(Wallet wallet, Long money);  
    Wallet findWalletById(Long id);  
    Wallet walletToWalletTransfer (User sender, Wallet receiverWallet, Long amount); 
    Wallet payOrderPayment (Order order, User user); 
    
}
