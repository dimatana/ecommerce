package com.training.ecommerce.Repository;

import com.training.ecommerce.NonDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NonDeliveryRepository extends JpaRepository<NonDelivery, Long> {
}
