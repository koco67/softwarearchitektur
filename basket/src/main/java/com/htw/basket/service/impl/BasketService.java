package com.htw.basket.service.impl;

import com.htw.basket.model.Basket;
import com.htw.basket.model.Product;
import com.htw.basket.service.interfaces.IBasketservice;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BasketService implements IBasketservice {

    // Simulating a database for storing baskets
    private List<Basket> baskets = new ArrayList<>();

    @Override
    public Basket addToBasket(Product product, String session) {
        if (product != null && session != null) {
            Basket basket = getOrCreateBasket(session);
            basket.addProduct(product);
            return basket;
        } else {
            throw new IllegalArgumentException("Product and session cannot be null.");
        }
    }

    @Override
    public Basket getBasket(String session) {
        if (session != null) {
            Basket basket = findBasket(session);
            if (basket != null) {
                return basket;
            } else {
                throw new IllegalStateException("Basket does not exist for the current session.");
            }
        } else {
            throw new IllegalArgumentException("Session cannot be null.");
        }
    }


    private Basket findBasket(String sessionId) {
        return baskets.stream()
                .filter(basket -> basket.getSessionId().equals(sessionId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Basket removeFromBasket(Product product, String session) {
        if (product != null && session != null) {
            Basket basket = getOrCreateBasket(session);
            basket.removeProduct(product);
            return basket;
        } else {
            throw new IllegalArgumentException("Product and session cannot be null.");
        }
    }

    private Basket getOrCreateBasket(String sessionId) {
        return baskets.stream()
                .filter(basket -> basket.getSessionId().equals(sessionId))
                .findFirst()
                .orElseGet(() -> {
                    Basket newBasket = new Basket(sessionId);
                    baskets.add(newBasket);
                    return newBasket;
                });
    }

    public String clearBasket(String session) {
        if (session != null) {
            baskets.removeIf(basket -> basket.getSessionId().equals(session));
            return "Basket successfully cleared ";
        } else {
            throw new IllegalArgumentException("Session cannot be null.");
        }
    }

    public List<Basket> getAllBaskets() {
        return baskets;
    }
}
