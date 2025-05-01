package com.milind.service;

import com.milind.domain.PaymentMethod;
import com.milind.modal.PaymentOrder;
import com.milind.modal.User;
import com.milind.response.PaymentResponse;

public interface PaymentService {
    PaymentOrder createOrder(User user, Long amount, PaymentMethod paymentMethod);

    PaymentOrder getPaymentOrderById(Long id);

    Boolean ProceedPaymentOrder(PaymentOrder paymentOrder, String paymentId, String signature);

    PaymentResponse createRazorpayPaymentLink(User user,Long amount);

    PaymentResponse createStripePaymentLink(User user,Long amount,Long orderId);


}
