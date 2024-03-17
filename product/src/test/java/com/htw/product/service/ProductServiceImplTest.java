package com.htw.product.service;

import com.htw.product.model.Product;
import com.htw.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService = new ProductServiceImpl();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("Product 1", "Description 1", 10.0, 5));
        productList.add(new Product("Product 2", "Description 2", 20.0, 10));

        when(productRepository.findAll()).thenReturn(productList);

        List<Product> result = productService.getAllProducts();

        assertEquals(2, result.size());
    }

    @Test
    void getProductById() {
        Long productId = 1L;
        Product product = new Product("Product 1", "Description 1", 10.0, 5);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(productId);

        assertEquals(product, result);
    }

    @Test
    void createProduct() {
        Product product = new Product("Product 1", "Description 1", 10.0, 5);

        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.createProduct(product);

        assertEquals(product, result);
    }

    @Test
    void updateProduct() {
        Long productId = 1L;
        Product existingProduct = new Product("Product 1", "Description 1", 10.0, 5);
        Product updatedProduct = new Product("Updated Product", "Updated Description", 15.0, 8);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(existingProduct);

        Product result = productService.updateProduct(productId, updatedProduct);
        assertAll(
                () -> assertEquals(updatedProduct.getPrice(), result.getPrice()),
                () -> assertEquals(updatedProduct.getDescription(), result.getDescription()),
                () -> assertEquals(updatedProduct.getInventory(), result.getInventory()),
                () -> assertEquals(updatedProduct.getName(), result.getName())
        );
    }

    @Test
    void deleteProduct() {
        Long productId = 1L;

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }
}