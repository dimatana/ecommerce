package com.training.ecommerce.repository;

import com.training.ecommerce.model.NonDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NonDeliveryRepository extends JpaRepository<NonDelivery, Long> {
}
