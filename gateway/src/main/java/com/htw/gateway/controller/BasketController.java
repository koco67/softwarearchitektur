package com.htw.gateway.controller;

import com.htw.gateway.entity.Basket;
import com.htw.gateway.entity.DefaultProduct;
import com.htw.gateway.entity.ProductDto;
import com.htw.gateway.service.BasketService;
import com.htw.gateway.service.ProductService;
import jakarta.servlet.http.HttpSession;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;
@RestController
@RequestMapping("/api/gateway")
@CrossOrigin(origins = "http://localhost:3000")
public class BasketController {

    private final BasketService basketService;
    private String savedSessionId;

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
    public Mono<ResponseEntity<Basket>> addToBasket(@RequestBody DefaultProduct defaultProduct, ServerWebExchange exchange) {
        return exchange.getSession()
                .flatMap(session -> {
                    if (savedSessionId == null) {
                        savedSessionId = session.getId();
                    }
                    return Mono.just(savedSessionId);
                })
                .flatMap(sessionId -> {
                    Basket basket = basketService.addToBasket(defaultProduct, sessionId);
                    return Mono.just(ResponseEntity.ok(basket));
                });
    }    

    @GetMapping("/basket")
    public ResponseEntity<Basket> getBasket() {
        Basket basket = basketService.getBasket(savedSessionId);
        return ResponseEntity.ok(basket);
    }

    @PutMapping("/basket/clear")
    public Mono<ResponseEntity<String>> clearBasket() {
        String message = basketService.clearBasket(savedSessionId);
        return Mono.just(ResponseEntity.ok(message));
    }

    @DeleteMapping("/basket/remove")
    public Mono<ResponseEntity<Basket>> removeFromBasket(@RequestBody DefaultProduct defaultProduct) {
        Basket basket = basketService.removeFromBasket(defaultProduct, savedSessionId);
        return Mono.just(ResponseEntity.ok(basket));
    }
}
