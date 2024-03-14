package com.htw.gateway.entity;

import java.util.ArrayList;
import java.util.List;

public class Basket {
    private String sessionId;
    private List<DefaultProduct> products = new ArrayList<>();

    public Basket(String sessionId) {
        this.sessionId = sessionId;
    }
}
