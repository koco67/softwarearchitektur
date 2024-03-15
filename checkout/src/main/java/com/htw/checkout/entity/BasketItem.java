package com.htw.checkout.entity;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class BasketItem {
    @Getter
    private List<DefaultProduct> products = new ArrayList<>();
    private String sessionId;

    public BasketItem(String sessionId) {
        this.sessionId = sessionId;
    }

}