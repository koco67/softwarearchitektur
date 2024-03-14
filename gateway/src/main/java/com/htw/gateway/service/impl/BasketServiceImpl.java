package com.htw.gateway.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.htw.gateway.entity.Basket;
import com.htw.gateway.entity.DefaultProduct;
import com.htw.gateway.entity.ProductDto;
import com.htw.gateway.error.ErrorResponseException;
import com.htw.gateway.service.BasketService;
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

public class BasketServiceImpl implements BasketService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private DirectExchange directExchange;
    @Value("${routing-keys.basket-service}")
    private String basketServiceRoutingKey;

    @Override
    public List<Basket> getAllBaskets() {
        var message = new Message("".getBytes());
        setMessageType(message, GET_ALL_BASKETS.name());
        var receivedMessage = rabbitTemplate.sendAndReceive(
                directExchange.getName(),
                basketServiceRoutingKey,
                message
        );
        if (receivedMessageIsError(receivedMessage)) {
            throw new ErrorResponseException("couldn't receive components");
            }
        return new Gson().fromJson(
                new String(receivedMessage.getBody(), StandardCharsets.UTF_8),
                new TypeToken<List<Basket>>() {
                }.getType()
        );
    }

    @Override
    public Basket getBasket(String session) {
        var message = new Message(session.getBytes());
        setMessageType(message, GET_BASKET.name());
        var receivedMessage = rabbitTemplate.sendAndReceive(
                directExchange.getName(),
                basketServiceRoutingKey,
                message
        );
        if (receivedMessageIsError(receivedMessage)) {
            throw new ErrorResponseException("couldn't receive components");
            }
        return new Gson().fromJson(
                new String(receivedMessage.getBody(), StandardCharsets.UTF_8),
                Basket.class
        );
    }

    @Override
    public Basket addToBasket(DefaultProduct product, String session) {
        // Create a map to hold productId and updatedProduct
        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("product", product);
        messageMap.put("session", session);

        // Convert the map to JSON
        String jsonMessage = new Gson().toJson(messageMap);

        var message = new Message(jsonMessage.getBytes());
        System.out.println(message);
        setMessageType(message, ADD_TO_BASKET.name());
        var receivedMessage = rabbitTemplate.sendAndReceive(
                directExchange.getName(),
                basketServiceRoutingKey,
                message
        );
        if (receivedMessageIsError(receivedMessage)) {
            throw new ErrorResponseException("couldn't receive components");
        }
        return new Gson().fromJson(
                new String(receivedMessage.getBody(), StandardCharsets.UTF_8),
                Basket.class
        );
    }

    @Override
    public Basket removeFromBasket(DefaultProduct product, String session) {
        // Create a map to hold productId and updatedProduct
        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("product", product);
        messageMap.put("session", session);

        // Convert the map to JSON
        String jsonMessage = new Gson().toJson(messageMap);


        var message = new Message(jsonMessage.getBytes());
        setMessageType(message, REMOVE_FROM_BASKET.name());
        var receivedMessage = rabbitTemplate.sendAndReceive(
                directExchange.getName(),
                basketServiceRoutingKey,
                message
        );
        if (receivedMessageIsError(receivedMessage)) {
            throw new ErrorResponseException("couldn't receive components");
        }
        return new Gson().fromJson(
                new String(receivedMessage.getBody(), StandardCharsets.UTF_8),
                Basket.class
        );
    }

    @Override
    public String clearBasket(String session) {
        var message = new Message(session.getBytes());
        setMessageType(message, CLEAR_BASKET.name());
        var receivedMessage = rabbitTemplate.sendAndReceive(
                directExchange.getName(),
                basketServiceRoutingKey,
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
