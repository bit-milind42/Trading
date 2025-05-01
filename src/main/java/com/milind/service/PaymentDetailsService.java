package com.milind.service;

import com.milind.modal.PaymentDetails;
import com.milind.modal.User;

public interface PaymentDetailsService {
    
    public PaymentDetails addPaymentDetails(String accountNumber, String accountHolderName, String ifsc, String bankName, User user);

    public PaymentDetails getUsersPaymentDetails(User user);
}
