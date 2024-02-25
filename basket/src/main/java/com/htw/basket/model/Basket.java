package com.htw.basket.model;

import com.htw.basket.model.Product;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Basket {
    private String sessionId;
    @Getter
    private List<Product> products;


    public Basket(String sessionId) {
        this.sessionId = sessionId;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }
}