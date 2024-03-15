package com.htw.gateway.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.htw.gateway.entity.Basket;
import com.htw.gateway.entity.DefaultProduct;
import com.htw.gateway.entity.Payment;
import com.htw.gateway.error.ErrorResponseException;
import com.htw.gateway.service.BasketService;
import com.htw.gateway.service.CheckoutService;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.htw.gateway.entity.MessageType.*;

public class CheckoutServiceImpl implements CheckoutService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private DirectExchange directExchange;
    @Value("${routing-keys.checkout-service}")
    private String checkoutServiceRoutingKey;


    @Override
    public double calculateTotal(Basket basket) {
        var message = new Message(new Gson().toJson(basket).getBytes());
        setMessageType(message, CALCULATE_TOTAL.name());
        var receivedMessage = rabbitTemplate.sendAndReceive(
                directExchange.getName(),
                checkoutServiceRoutingKey,
                message
        );
        if (receivedMessageIsError(receivedMessage)) {
            throw new ErrorResponseException("couldn't receive components");
        }
        return new Gson().fromJson(
                new String(receivedMessage.getBody(), StandardCharsets.UTF_8),
                double.class
        );
    }

    @Override
    public String proceedToPayment(Payment payment) {
        var message = new Message(new Gson().toJson(payment).getBytes());
        setMessageType(message, PROCEED_TO_PAYMENT.name());
        var receivedMessage = rabbitTemplate.sendAndReceive(
                directExchange.getName(),
                checkoutServiceRoutingKey,
                message
        );
        if (receivedMessageIsError(receivedMessage)) {
            throw new ErrorResponseException("couldn't receive components");
        }
        return new Gson().fromJson(
                new String(receivedMessage.getBody(), StandardCharsets.UTF_8),
                String.class
        );
    }

    private void setMessageType(Message message, String type) {
        message.getMessageProperties()
                .setType(type);
    }

    private boolean receivedMessageIsError(Message receivedMessage) {
        return receivedMessage == null ||
                receivedMessage.getBody() == null ||
                new String(receivedMessage.getBody(), StandardCharsets.UTF_8).equals("errorResponse");
    }
}
