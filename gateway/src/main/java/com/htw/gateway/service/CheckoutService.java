package com.htw.gateway.service;

import com.htw.gateway.entity.Basket;
import com.htw.gateway.entity.Payment;

public interface CheckoutService {
    double calculateTotal(Basket basket);
    public String proceedToPayment(Payment payment);
}
