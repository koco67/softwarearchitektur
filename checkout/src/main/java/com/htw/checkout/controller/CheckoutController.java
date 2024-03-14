package com.htw.checkout.controller;

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
    public ResponseEntity<Double> calculateTotal() {
        double total = checkoutService.calculateTotal();
        return ResponseEntity.ok(total);
    }

    @PostMapping("/payment")
    public ResponseEntity<String> proceedToPayment() {
        // Dummy payment logic
        return ResponseEntity.ok("Proceeding to payment (dummy response)");
    }
}
