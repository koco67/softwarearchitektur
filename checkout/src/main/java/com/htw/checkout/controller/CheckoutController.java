package com.htw.checkout.controller;

import com.htw.checkout.entity.BasketItem;
import com.htw.checkout.entity.Payment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.htw.checkout.service.CheckoutService;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @GetMapping("/total")
    public ResponseEntity<Double> calculateTotal(BasketItem basketItem) {
        return ResponseEntity.ok(checkoutService.calculateTotal(basketItem));
    }

    @PostMapping("/payment")
    public ResponseEntity<String> proceedToPayment(Payment payment) {
        return ResponseEntity.ok(checkoutService.proceedToPayment(payment));
    }
}
