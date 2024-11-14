package com.training.ecommerce.Repository;

import com.training.ecommerce.HubRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HubRateRepository extends JpaRepository<HubRate, Long> {
}
