package com.htw.checkout.service.impl;

import com.htw.checkout.entity.BasketItem;
import com.htw.checkout.entity.DefaultProduct;
import com.htw.checkout.entity.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutServiceImplTest {

    private CheckoutServiceImpl checkoutService;

    @BeforeEach
    void setUp() {
        checkoutService = new CheckoutServiceImpl();
    }

    @Test
    void calculateTotal() {
        List<DefaultProduct> products = new ArrayList<>();
        products.add(new DefaultProduct(1L, "Test Product 1", "Description 1", 10.0, 5));
        products.add(new DefaultProduct(2L, "Test Product 2", "Description 2", 20.0, 10));
        BasketItem basketItem = new BasketItem("testSession");
        basketItem.getProducts().addAll(products);

        double totalPrice = checkoutService.calculateTotal(basketItem);

        assertEquals(30.0, totalPrice);
    }

    @Test
    void proceedToPayment() {
        Payment payment = new Payment();
        payment.setZIP("12345");
        payment.setStreet("Test Street");
        payment.setCity("Test City");
        payment.setCountry("Test Country");
        payment.setIBAN("Test IBAN");

        String result = checkoutService.proceedToPayment(payment);

        assertEquals("accepted Payment", result);
    }
}