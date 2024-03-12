package com.htw.gateway.entity;

public class ProductMessage {

    private String action;
    private Long productId;
    private ProductDto product;

    // Constructors, getters, and setters

    public ProductMessage() {
        // Default constructor
    }

    public ProductMessage(String action, Long productId, ProductDto product) {
        this.action = action;
        this.productId = productId;
        this.product = product;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }
}
