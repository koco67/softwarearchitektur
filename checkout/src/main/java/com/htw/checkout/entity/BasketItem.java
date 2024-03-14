package com.htw.checkout.entity;

import java.util.ArrayList;
import java.util.List;

public class BasketItem {
    private List<DefaultProduct> products = new ArrayList<>();
    private String sessionId;

    public BasketItem(String sessionId) {
        this.sessionId = sessionId;
    }
}