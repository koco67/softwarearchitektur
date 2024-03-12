package com.htw.gateway.service.impl;

import com.htw.gateway.entity.DefaultProduct;
import com.htw.gateway.entity.ProductDto;
import com.htw.gateway.service.ProductService;
import com.htw.gateway.error.ErrorResponseException;

import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import static com.htw.gateway.entity.MessageType.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ProductServiceImpl implements ProductService{

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private DirectExchange directExchange;
    @Value("${routing-keys.product-service}")
    private String productServiceRoutingKey;
    
    @Override
    public List<DefaultProduct> getAllProducts() {

        var message = new Message("".getBytes());
        setMessageType(message, GET_ALL_PRODUCTS.name());
        var receivedMessage = rabbitTemplate.sendAndReceive(
                directExchange.getName(),
                productServiceRoutingKey,
                message
        );
        if (receivedMessageIsError(receivedMessage)) {
                throw new ErrorResponseException("couldn't receive components");
            }
        return new Gson().fromJson(
                new String(receivedMessage.getBody(), StandardCharsets.UTF_8),
                new TypeToken<List<DefaultProduct>>() {
                }.getType()
        );
    }


    @Override
    public DefaultProduct getProductById(Long productId) {
        var message = new Message(productId.toString().getBytes());
        setMessageType(message, GET_PRODUCT_BY_ID.name());
        var receivedMessage = rabbitTemplate.sendAndReceive(
                directExchange.getName(),
                productServiceRoutingKey,
                message
        );
        return new Gson().fromJson(
                new String(receivedMessage.getBody(), StandardCharsets.UTF_8),
                DefaultProduct.class
        );
    }

    @Override
    public ProductDto createProduct(ProductDto product) {
        var message = new Message(new Gson().toJson(product).getBytes());
        setMessageType(message, CREATE_PRODUCT.name());
        var receivedMessage = rabbitTemplate.sendAndReceive(
                directExchange.getName(),
                productServiceRoutingKey,
                message
        );
        return new Gson().fromJson(
                new String(receivedMessage.getBody(), StandardCharsets.UTF_8),
                ProductDto.class
        );
    }

    @Override
    public DefaultProduct updateProduct(Long productId, ProductDto updatedProduct) {
        var message = new Message(new Gson().toJson(updatedProduct).getBytes());
        setMessageType(message, UPDATE_PRODUCT.name());
        var receivedMessage = rabbitTemplate.sendAndReceive(
                directExchange.getName(),
                productServiceRoutingKey,
                message
        );
        return new Gson().fromJson(
                new String(receivedMessage.getBody(), StandardCharsets.UTF_8),
                DefaultProduct.class
        );
    }

    @Override
    public DefaultProduct deleteProduct(Long productId) {
        var message = new Message(productId.toString().getBytes());
        setMessageType(message, DELETE_PRODUCT.name());
        var receivedMessage = rabbitTemplate.sendAndReceive(
                directExchange.getName(),
                productServiceRoutingKey,
                message
        );
        return new Gson().fromJson(
                new String(receivedMessage.getBody(), StandardCharsets.UTF_8),
                DefaultProduct.class
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
