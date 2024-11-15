package com.training.ecommerce.repository;

import com.training.ecommerce.model.Basket;
import com.training.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {
    Basket findByUser(User user);
}
