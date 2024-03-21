package com.htw.basket.service.interfaces;

import com.htw.basket.model.Basket;
import com.htw.basket.model.Product;


import java.util.List;

public interface IBasketservice {

    Basket addToBasket(Product product, String session);
    Basket getBasket(String session);
    Basket removeFromBasket(Product product, String session);
    String clearBasket(String session);
    List<Basket> getAllBaskets();
}