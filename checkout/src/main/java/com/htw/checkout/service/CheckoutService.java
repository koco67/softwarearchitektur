package com.htw.checkout.service;

import org.springframework.stereotype.Service;

@Service
public class CheckoutService {

    public double calculateTotal() {
        // You can calculate the total from the basket
        // Logic to fetch basket items from database or another service
        // Calculate total based on basket items
        return 100.0; // Dummy total for now
    }
}
