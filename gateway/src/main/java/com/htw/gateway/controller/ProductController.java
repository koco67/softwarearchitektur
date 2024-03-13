package com.htw.gateway.controller;

import com.htw.gateway.entity.DefaultProduct;
import com.htw.gateway.entity.ProductDto;
import com.htw.gateway.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/gateway")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<DefaultProduct>> getAllProducts() {
        List<DefaultProduct> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PostMapping("/products")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductDto createdProduct = productService.createProduct(productDto);
        return ResponseEntity.ok(createdProduct);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<DefaultProduct> getProductById(@PathVariable Long productId) {
        DefaultProduct product = productService.getProductById(productId);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<DefaultProduct> updateProduct(@PathVariable Long productId, @RequestBody ProductDto updatedProductDto) {
        DefaultProduct updatedProduct = productService.updateProduct(productId, updatedProductDto);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<DefaultProduct> deleteProduct(@PathVariable Long productId) {
        DefaultProduct deletedProduct = productService.deleteProduct(productId);
        return ResponseEntity.ok(deletedProduct);
    }

}
