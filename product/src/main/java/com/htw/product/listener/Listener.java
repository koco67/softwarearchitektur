package com.htw.product.listener;

import java.nio.charset.StandardCharsets;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.ErrorResponseException;
import com.google.gson.Gson;
import com.htw.product.entity.MessageType;
import com.htw.product.model.Product;
import com.htw.product.service.ProductService;

public class Listener {

    @Autowired
    private ProductService productService;

    @RabbitListener(queues = "${queue-names.product-service}")
    public String handleRequest(Message message) {
        System.out.print("handle request ");
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
                    System.out.print("in case ");

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
                    Product product = extractProductFrom(message);
                    long productId = extractProductIdFrom(message);
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

    private long extractProductIdFrom(Message message) {
        return Long.parseLong(getBodyFrom(message));
    }

    private Product extractProductFrom(Message message) {
        return new Gson().fromJson(getBodyFrom(message), Product.class);
    }

    private String getBodyFrom(Message message) {
        return new String(message.getBody(), StandardCharsets.UTF_8);
    }

    private String logInvalidMessageType(String type) {
        return errorResponse();
    }

    private String updateProduct(long productId,Product product) throws ErrorResponseException {
        return new Gson().toJson(productService.updateProduct(productId, product));
    }

    private String createProduct(Product product) throws ErrorResponseException {
        return new Gson().toJson(productService.createProduct(product));
    }

    private String deleteProductById(long productId) throws ErrorResponseException {
        productService.deleteProduct(productId);
        return "deleted";
    }

    private String getAllProducts() {
        System.out.print("in method get all products ");
        return new Gson().toJson(productService.getAllProducts());
    }

    private String getProductById(long productId) {
        return new Gson().toJson(productService.getProductById(productId));
    }


}
