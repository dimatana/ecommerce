package com.training.ecommerce.controller;

import com.training.ecommerce.model.Basket;
import com.training.ecommerce.service.BasketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/baskets")
public class BasketController {
    private final BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }
    @GetMapping("/{userId}")
    public ResponseEntity<Basket> getBasketByUserId(@PathVariable Long userId){
        Basket basket = basketService.getBasketByUserId(userId);
        return ResponseEntity.ok(basket);
    }
    @GetMapping("/{userId}/add")
    public ResponseEntity<Basket> addItemToBasket(@PathVariable Long userId, @RequestParam Long productId, @RequestParam int quantity){
        Basket basket = basketService.addItemToBasket(userId, productId, quantity);
        return ResponseEntity.ok(basket);
    }
}
