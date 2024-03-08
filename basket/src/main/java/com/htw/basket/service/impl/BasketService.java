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
    public void addToBasket(Product product, HttpSession session) {
        if (product != null && session != null) {
            String sessionId = session.getId();
            Basket basket = getOrCreateBasket(sessionId);
            basket.addProduct(product);
        } else {
            throw new IllegalArgumentException("Product and session cannot be null.");
        }
    }

    @Override
    public Basket getBasket(HttpSession session) {
        if (session != null) {
            String sessionId = session.getId();
            Basket basket = findBasket(sessionId);
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
    public void removeFromBasket(Product product, HttpSession session) {
        if (product != null && session != null) {
            String sessionId = session.getId();
            Basket basket = getOrCreateBasket(sessionId);
            basket.removeProduct(product);
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

    public void clearBasket(HttpSession session) {
        if (session != null) {
            String sessionId = session.getId();
            baskets.removeIf(basket -> basket.getSessionId().equals(sessionId));
        } else {
            throw new IllegalArgumentException("Session cannot be null.");
        }
    }
    public List<Basket> getAllBaskets() {
        return baskets;
    }
}
