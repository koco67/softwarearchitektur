package com.htw.basket.service.interfaces;

import com.htw.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
public interface IProductRepository extends JpaRepository<Product, UUID> {

    ArrayList<Product> findByProductID(UUID id);
    void deleteAllByProductID_CartID(String cartID);
}