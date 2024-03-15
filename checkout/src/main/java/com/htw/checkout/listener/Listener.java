package com.htw.checkout.listener;

import java.nio.charset.StandardCharsets;

import com.htw.checkout.entity.Payment;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.ErrorResponseException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.htw.checkout.entity.BasketItem;
import com.htw.checkout.entity.MessageType;
import com.htw.checkout.service.CheckoutService;
import com.google.gson.JsonParser;

public class Listener {

    @Autowired
    private CheckoutService checkoutService;

    @RabbitListener(queues = "${queue-names.checkout-service}")
    public String handleRequest(Message message) {

        final MessageType messageType;
        try {
            messageType = MessageType.valueOf(message.getMessageProperties().getType());
        } catch (IllegalArgumentException e) {
            return logInvalidMessageType(message.getMessageProperties().getType());
        }

        try {
            switch (messageType) {
                case CALCULATE_TOTAL: {
                    BasketItem basket = extractBasketItem(message);
                    return calculateTotal(basket);
                }
                case PROCEED_TO_PAYMENT: {
                    Payment payment = extractPayment(message);
                    return proceedToPayment(payment);
                }
                default: {
                    return errorResponse();
                }
            }
        } catch (ErrorResponseException e) {
            return errorResponse();
        }
    }

    private String errorResponse() {
        return "errorResponse";
    }

    private BasketItem extractBasketItem(Message message) {
        return new Gson().fromJson(getBodyFrom(message), BasketItem.class);
    }

    private Payment extractPayment(Message message) {
        return new Gson().fromJson(getBodyFrom(message), Payment.class);
    }

    private String getBodyFrom(Message message) {
        return new String(message.getBody(), StandardCharsets.UTF_8);
    }

    private String logInvalidMessageType(String type) {
        return errorResponse();
    }

    private String calculateTotal(BasketItem basket) {
        return new Gson().toJson(checkoutService.calculateTotal(basket));
    }
    private String proceedToPayment(Payment payment) {
        return checkoutService.proceedToPayment(payment);
    }
    
}
