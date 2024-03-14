package com.htw.gateway.service;

import com.htw.gateway.entity.Basket;
import com.htw.gateway.entity.DefaultProduct;
import com.htw.gateway.entity.ProductDto;

import java.util.List;

public interface BasketService {

    Basket addToBasket(DefaultProduct product, String session);
    Basket getBasket(String session);
    Basket removeFromBasket(DefaultProduct product, String session);
    public String clearBasket(String session);
    public List<Basket> getAllBaskets();
}
