package com.milind.service;

import com.milind.modal.User;
import com.milind.modal.Withdrawal;

public interface WithdrawalService{

    Withdrawal requestWithdrawal(Long amount,User user);
    Withdrawal procedWithwithdrawal (Long withdrawalId, boolean accept); 
    List<Withdrawal> getUsersWithdrawalHistory(User user); 
    List<Withdrawal> getAllWithdrawalRequest(); 

}