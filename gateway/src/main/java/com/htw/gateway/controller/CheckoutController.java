package com.htw.gateway.controller;

import com.htw.gateway.entity.Basket;
import com.htw.gateway.entity.DefaultProduct;
import com.htw.gateway.entity.Payment;
import com.htw.gateway.entity.ProductDto;
import com.htw.gateway.service.CheckoutService;
import com.htw.gateway.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/gateway")
@CrossOrigin(origins = "http://localhost:3000")
public class CheckoutController {

    private final CheckoutService checkoutService;

    @Autowired
    public CheckoutController(CheckoutService checkoutService) {this.checkoutService = checkoutService;
    }

    @GetMapping("/checkout/total")
    public ResponseEntity<Double> calculateTotal(Basket basket) {
        return ResponseEntity.ok(checkoutService.calculateTotal(basket));
    }

    @PostMapping("/checkout/payment")
    public ResponseEntity<String> proceedToPayment(Payment payment) {
        return ResponseEntity.ok(checkoutService.proceedToPayment(payment));
    }
}
