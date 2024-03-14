package com.htw.checkout.service;

import org.springframework.stereotype.Service;

import com.htw.checkout.entity.BasketItem;

@Service
public interface CheckoutService {

    double calculateTotal(BasketItem basket);
}
