package com.htw.gateway.service;

import java.util.List;

import com.htw.gateway.entity.DefaultProduct;
import com.htw.gateway.entity.ProductDto;


public interface ProductService {

    List<DefaultProduct> getAllProducts();

    DefaultProduct getProductById(Long productId);

    ProductDto createProduct(ProductDto product);

    DefaultProduct updateProduct(Long productId, ProductDto updatedProduct);

    DefaultProduct deleteProduct(Long productId);
}