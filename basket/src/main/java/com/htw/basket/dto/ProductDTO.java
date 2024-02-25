package com.htw.basket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private UUID productID;
    private String productName;
    private Double productPrice;
    private String productImg;
    private int quantity;


    @Override
    public String toString() {
        return "ProductDTO{" +
                "productID=" + productID +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productImg='" + productImg + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
