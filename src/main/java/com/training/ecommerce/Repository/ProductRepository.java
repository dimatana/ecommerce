package com.training.ecommerce.Repository;

import com.training.ecommerce.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository <Product, Long> {
}
