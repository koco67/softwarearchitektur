package com.htw.product.listener;

import java.nio.charset.StandardCharsets;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.ErrorResponseException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.htw.product.entity.MessageType;
import com.htw.product.model.Product;
import com.htw.product.service.ProductService;
import com.google.gson.JsonParser;

public class Listener {

    @Autowired
    private ProductService productService;

    @RabbitListener(queues = "${queue-names.product-service}")
    public String handleRequest(Message message) {

        final MessageType messageType;
        try {
            messageType = MessageType.valueOf(message.getMessageProperties().getType());
        } catch (IllegalArgumentException e) {
            return logInvalidMessageType(message.getMessageProperties().getType());
        }

        try {
            switch (messageType) {
                case GET_PRODUCT_BY_ID: {
                    long productId = extractProductIdFrom(message);
                    return getProductById(productId);
                }
                case GET_ALL_PRODUCTS: {
                    return getAllProducts();
                }
                case DELETE_PRODUCT: {
                    long productId = extractProductIdFrom(message);
                    return deleteProductById(productId);
                }
                case CREATE_PRODUCT: {
                    Product product = extractProductFrom(message);
                    return createProduct(product);
                }
                case UPDATE_PRODUCT: {
                    System.out.println(message);
                    Long productId = extractProductIdFromUpdate(message);
                    Product product = extractProductFromUpdate(message);
                    return updateProduct(productId, product);
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

    private Long extractProductIdFrom(Message message) {
        return Long.parseLong(getBodyFrom(message));
    }
    
    private Product extractProductFrom(Message message) {
        return new Gson().fromJson(getBodyFrom(message), Product.class);
    }

    private Long extractProductIdFromUpdate(Message message) {
        String jsonMessage = new String(message.getBody(), StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(jsonMessage).getAsJsonObject();
        return jsonObject.getAsJsonPrimitive("productId").getAsLong();
    }
    
    private Product extractProductFromUpdate(Message message) {
        String jsonMessage = new String(message.getBody(), StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(jsonMessage).getAsJsonObject();
        JsonObject productJson = jsonObject.getAsJsonObject("updatedProduct");
        return new Gson().fromJson(productJson, Product.class);
    }
    private String getBodyFrom(Message message) {
        return new String(message.getBody(), StandardCharsets.UTF_8);
    }

    private String logInvalidMessageType(String type) {
        return errorResponse();
    }

    private String updateProduct(long productId, Product product) throws ErrorResponseException {
        return new Gson().toJson(productService.updateProduct(productId, product));
    }

    private String createProduct(Product product) throws ErrorResponseException {
        return new Gson().toJson(productService.createProduct(product));
    }

    private String deleteProductById(long productId) throws ErrorResponseException {
        productService.deleteProduct(productId);
        return "{\"message\": \"Product deleted successfully\"}";
    }

    private String getAllProducts() {
        return new Gson().toJson(productService.getAllProducts());
    }

    private String getProductById(long productId) {
        return new Gson().toJson(productService.getProductById(productId));
    }
    
}
