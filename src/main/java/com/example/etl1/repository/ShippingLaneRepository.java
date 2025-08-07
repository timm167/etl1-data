package com.example.etl1.repository;

import com.example.etl1.model.ShippingLane;
import org.springframework.data.repository.CrudRepository;

public interface ShippingLaneRepository extends CrudRepository<ShippingLane, Long> {
}