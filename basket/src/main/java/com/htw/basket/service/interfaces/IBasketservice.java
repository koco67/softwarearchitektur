package com.htw.basket.service.interfaces;

import com.htw.basket.dto.BasketDTO;
import com.htw.basket.model.Basket;
import com.htw.basket.model.Product;

import javax.servlet.http.HttpSession;

public interface IBasketservice {

    void addToBasket(Product product, HttpSession session);
    Basket getBasket(HttpSession session);
    void removeFromBasket(Product product, HttpSession session);

}