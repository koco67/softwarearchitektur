package com.htw.checkout.service.impl;

import com.htw.checkout.entity.BasketItem;
import com.htw.checkout.entity.DefaultProduct;
import com.htw.checkout.entity.Payment;
import com.htw.checkout.service.CheckoutService;

import java.util.List;

public class CheckoutServiceImpl implements CheckoutService{

    @Override
    public double calculateTotal(BasketItem basketItem) {
            List<DefaultProduct> products = basketItem.getProducts();
            double totalPrice = 0.0;

            for (DefaultProduct product : products) {
                totalPrice += product.getPrice();
            }
            return totalPrice;
    }
    public String proceedToPayment(Payment payment) {
        //send Payment details to warehouse
        return "accepted Payment";
    }
}
