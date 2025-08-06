package com.example.etl1.repository;

import com.example.etl1.model.ShippingLane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingLaneRepository extends JpaRepository<ShippingLane, Long> {
}