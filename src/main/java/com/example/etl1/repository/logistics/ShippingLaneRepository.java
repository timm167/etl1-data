package com.example.etl1.repository.logistics;

import com.example.etl1.model.logistics.ShippingLane;
import org.springframework.data.repository.CrudRepository;

public interface ShippingLaneRepository extends CrudRepository<ShippingLane, Integer> {
}