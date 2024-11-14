package com.training.ecommerce.repository;

import com.training.ecommerce.model.HubRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HubRateRepository extends JpaRepository<HubRate, Long> {
}
