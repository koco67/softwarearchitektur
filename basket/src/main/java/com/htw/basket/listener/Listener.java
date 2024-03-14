package com.htw.basket.listener;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.htw.basket.service.interfaces.IBasketservice;
import com.htw.basket.model.MessageType;
import com.htw.basket.model.Product;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.ErrorResponseException;

import java.nio.charset.StandardCharsets;

public class Listener {

    @Autowired
    private IBasketservice basketService;

    @RabbitListener(queues = "${queue-names.basket-service}")
    public String handleRequest(Message message) {

        final MessageType messageType;
        try {
            messageType = MessageType.valueOf(message.getMessageProperties().getType());
        } catch (IllegalArgumentException e) {
            return logInvalidMessageType(message.getMessageProperties().getType());
        }

        try {
            switch (messageType) {
                case GET_BASKET: {
                    String session = extractSession(message);
                    return getBasket(session);
                }
                case GET_ALL_BASKETS: {
                    return getAllBaskets();
                }
                case REMOVE_FROM_BASKET: {
                    Product product = extractProductFromTwo(message);
                    String session = extractSessionFromTwo(message);
                    return removeFromBasket(product, session);
                }
                case ADD_TO_BASKET: {
                    Product product = extractProductFromTwo(message);
                    String session = extractSessionFromTwo(message);
                    return addToBasket(product, session);
                }
                case CLEAR_BASKET: {
                    String session = extractSessionFromTwo(message);
                    return clearBasket(session);
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

        private String extractSession(Message message) {
        return getBodyFrom(message);
    }

        private Product extractProduct(Message message) {
        return new Gson().fromJson(getBodyFrom(message), Product.class);
    }

        private String extractSessionFromTwo(Message message) {
        String jsonMessage = new String(message.getBody(), StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(jsonMessage).getAsJsonObject();
        return jsonObject.getAsJsonPrimitive("session").getAsString();
    }

        private Product extractProductFromTwo(Message message) {
        String jsonMessage = new String(message.getBody(), StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(jsonMessage).getAsJsonObject();
        JsonObject productJson = jsonObject.getAsJsonObject("product");
        return new Gson().fromJson(productJson, Product.class);
    }
        private String getBodyFrom(Message message) {
        return new String(message.getBody(), StandardCharsets.UTF_8);
    }

        private String logInvalidMessageType(String type) {
        return errorResponse();
    }

        private String addToBasket(Product product, String session) throws ErrorResponseException {
        return new Gson().toJson(basketService.addToBasket(product, session));
    }

        private String removeFromBasket(Product product, String session) throws ErrorResponseException {
        return new Gson().toJson(basketService.removeFromBasket(product, session));
    }

        private String getBasket(String session) throws ErrorResponseException {
        return new Gson().toJson(basketService.getBasket(session));
    }

        private String getAllBaskets() {
        return new Gson().toJson(basketService.getAllBaskets());
    }

        private String clearBasket(String session) {
        return basketService.clearBasket(session);
    }
}
