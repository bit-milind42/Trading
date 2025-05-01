package com.milind.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.milind.modal.PaymentDetails;
import com.milind.modal.User;
import com.milind.repository.PaymentDetailsRepository;

@Service
public class PaymentDetailsServiceImpl implements PaymentDetailsService {
   
    @Autowired
    private PaymentDetailsRepository paymentDetailsRepository;
    

    @Override
    public PaymentDetails addPaymentDetails(String accountNumber, String accountHolderName, String ifsc, String bankName, User user) {
        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setAccountNumber(accountNumber);
        paymentDetails.setAccountHolderName(accountHolderName);
        paymentDetails.setIfsc(ifsc);
        paymentDetails.setBankName(bankName);
        paymentDetails.setUser(user);

        return paymentDetailsRepository.save(paymentDetails); 
    }

    @Override
    public PaymentDetails getUsersPaymentDetails(User user) {

        return paymentDetailsRepository.findByUserId(user.getId()); 
    }
    
}
