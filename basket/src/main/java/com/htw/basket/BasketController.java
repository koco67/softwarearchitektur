/* only for testing purposes
package com.htw.basket;

import com.htw.basket.model.Basket;
import com.htw.basket.model.Product;
import com.htw.basket.service.impl.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.List;

@RestController
public class BasketController {

    @Autowired
    private BasketService basketService;

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")     //Temporary fix
    @PostMapping("/basket/add")
    public String addToBasket(@RequestBody Product product, HttpSession session) {
        basketService.addToBasket(product, session.getId());
        return "Product with ID " + product.getId() + " added to basket.";
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")     //Temporary fix
    @GetMapping("/basket/view")
    public Basket viewBasket(HttpSession session) {
        return basketService.getBasket(session.getId());
    }

    @DeleteMapping("/basket/remove")
    public String removeFromBasket(@RequestBody Product product, HttpSession session) {
        basketService.removeFromBasket(product, session.getId());
        return "Product with ID " + product.getId() + " removed from basket.";
    }
    @DeleteMapping("/basket/clear")
    public String clearBasket(HttpSession session) {
        basketService.clearBasket(session.getId());
        return "Basket cleared.";
    }
    @GetMapping("/baskets")
    public List<Basket> getAllBaskets() {
        return basketService.getAllBaskets();
    }
}
*/