package com.htw.checkout.service;

import com.htw.checkout.entity.Payment;
import org.springframework.stereotype.Service;

import com.htw.checkout.entity.BasketItem;

@Service
public interface CheckoutService {

    double calculateTotal(BasketItem basket);
    public String proceedToPayment(Payment payment);
}
