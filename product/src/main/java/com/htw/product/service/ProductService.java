package com.htw.product.service;

import com.htw.product.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    Product getProductById(Long productId);

    Product createProduct(Product product);

    Product updateProduct(Long productId, Product updatedProduct);

    void deleteProduct(Long productId);
}
