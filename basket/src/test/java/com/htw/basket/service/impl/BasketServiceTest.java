package com.htw.basket.service.impl;

import com.htw.basket.model.Basket;
import com.htw.basket.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BasketServiceTest {

    private BasketService basketService;

    @BeforeEach
    public void setUp() {
        basketService = new BasketService();
        }
    @Test
    void addToBasket() {
        String session = "testSession";
        Product product = new Product("Test Product", "123", 10.0);

        Basket basket = basketService.addToBasket(product, session);

        assertNotNull(basket);
        assertEquals(1, basket.getProducts().size());
        assertEquals(product, basket.getProducts().get(0));
    }

    @Test
    void getBasket() {
        String session = "testSession";
        Basket basket = basketService.addToBasket(new Product("Test Product", "123", 10.0), session);

        Basket retrievedBasket = basketService.getBasket(session);

        assertNotNull(retrievedBasket);
        assertEquals(basket, retrievedBasket);
    }

    @Test
    void removeFromBasket() {
        String session = "testSession";
        Product product = new Product("Test Product", "123", 10.0);

        basketService.addToBasket(product, session);
        Basket basket = basketService.removeFromBasket(product, session);

        assertNotNull(basket);
        assertTrue(basket.getProducts().isEmpty());
    }

    @Test
    void clearBasket() {
        String session = "testSession";
        basketService.addToBasket(new Product("Test Product", "123", 10.0), session);

        String result = basketService.clearBasket(session);

        assertEquals("Basket successfully cleared ", result);
        assertTrue(basketService.getAllBaskets().isEmpty());
    }

    @Test
    void getAllBaskets() {
        basketService.addToBasket(new Product("Test Product 1", "123", 10.0), "session1");
        basketService.addToBasket(new Product("Test Product 2", "456", 20.0), "session2");

        List<Basket> baskets = basketService.getAllBaskets();

        assertEquals(2, baskets.size());
    }
}