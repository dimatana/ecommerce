package com.training.ecommerce.service;

import com.training.ecommerce.model.Basket;
import com.training.ecommerce.model.BasketItem;
import com.training.ecommerce.model.Product;
import com.training.ecommerce.model.User;
import com.training.ecommerce.repository.BasketItemRepository;
import com.training.ecommerce.repository.BasketRepository;
import com.training.ecommerce.repository.ProductRepository;
import com.training.ecommerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class BasketService {
    private final BasketRepository basketRepository;
    private final BasketItemRepository basketItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public BasketService(BasketRepository basketRepository,
                         BasketItemRepository basketItemRepository,
                         UserRepository userRepository,
                         ProductRepository productRepository) {
        this.basketRepository = basketRepository;
        this.basketItemRepository = basketItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }
    @Transactional
    public Basket getBasketByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
        return basketRepository.findByUser(user);
    }
    @Transactional
    public Basket addItemToBasket(Long userId, Long productId, int quantity){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Basket basket = basketRepository.findByUser(user);

        if (basket == null){
            basket = new Basket();
            basket.setUser(user);
            basketRepository.save(basket);
        }
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("product not found"));
        BasketItem basketItem = new BasketItem();
        basketItem.setBasket(basket);
        basketItem.setProduct(product);
        basketItem.setQuantity(quantity);

        basket.getItems().add(basketItem);
        return basketRepository.save(basket);
    }
}
