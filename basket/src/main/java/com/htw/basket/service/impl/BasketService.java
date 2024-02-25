package com.htw.basket.service.impl;

import com.htw.basket.model.Basket;
import com.htw.basket.model.Product;
import com.htw.basket.service.interfaces.IBasketservice;
import com.htw.basket.service.interfaces.IProductRepository;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BasketService implements IBasketservice {
    @Autowired
    IProductRepository productRepository;
    Basket basket;

    public BasketService(IProductRepository productRepository) {
        super();
        this.productRepository = productRepository;
    }

    // Simulating a database for storing baskets
    private List<Basket> baskets = new ArrayList<>();

    @Override
    public void addToBasket(Product product, HttpSession session) {
        String sessionId = session.getId();
        Basket basket = getOrCreateBasket(sessionId);
        basket.addProduct(product);
    }
    @Override
    public Basket getBasket(HttpSession session) {
        String sessionId = session.getId();
        return getOrCreateBasket(sessionId);
    }
    @Override
    public void removeFromBasket(Product product, HttpSession session) {
        String sessionId = session.getId();
        Basket basket = getOrCreateBasket(sessionId);
        basket.removeProduct(product);
    }

    private Basket getOrCreateBasket(String sessionId) {
        for (Basket basket : baskets) {
            if (basket.getSessionId().equals(sessionId)) {
                return basket;
            }
        }
        Basket newBasket = new Basket(sessionId);
        baskets.add(newBasket);
        return newBasket;
    }
}
