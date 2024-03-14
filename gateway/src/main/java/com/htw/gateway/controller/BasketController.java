package com.htw.gateway.controller;

import com.htw.gateway.entity.Basket;
import com.htw.gateway.entity.DefaultProduct;
import com.htw.gateway.entity.ProductDto;
import com.htw.gateway.service.BasketService;
import com.htw.gateway.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gateway")
@CrossOrigin(origins = "http://localhost:3000")
public class BasketController {

    private final BasketService basketService;

    @Autowired
    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @GetMapping("/baskets")
    public ResponseEntity<List<Basket>> getAllBaskets() {
        List<Basket> baskets = basketService.getAllBaskets();
        return ResponseEntity.ok(baskets);
    }

    @PostMapping("/basket/add")
    public ResponseEntity<Basket> addToBasket(@RequestBody DefaultProduct defaultProduct, HttpSession session) {
        Basket basket = basketService.addToBasket(defaultProduct, session.getId());
        return ResponseEntity.ok(basket);
    }

    @GetMapping("/basket")
    public ResponseEntity<Basket> getBasket(@RequestBody HttpSession session) {
        Basket basket = basketService.getBasket(session.getId());
        return ResponseEntity.ok(basket);
    }

    @PutMapping("/basket/clear")
    public ResponseEntity<String> clearBasket(@RequestBody HttpSession session) {
        String message = basketService.clearBasket(session.getId());
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/basket/remove")
    public ResponseEntity<Basket> removeFromBasket(@RequestBody DefaultProduct defaultProduct, HttpSession session) {
        Basket basket = basketService.removeFromBasket(defaultProduct, session.getId());
        return ResponseEntity.ok(basket);
    }

}
