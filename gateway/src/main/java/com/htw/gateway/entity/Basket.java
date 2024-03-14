package com.htw.gateway.entity;

import java.util.ArrayList;
import java.util.List;

public class Basket {
    private String sessionId;
    private List<DefaultProduct> products = new ArrayList<>();

    public Basket() {
        // Default constructor required by Jackson for deserialization
    }

    public Basket(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public List<DefaultProduct> getProducts() {
        return products;
    }

    public void setProducts(List<DefaultProduct> products) {
        this.products = products;
    }
}
