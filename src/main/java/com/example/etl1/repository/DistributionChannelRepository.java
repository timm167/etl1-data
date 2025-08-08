package com.example.etl1.repository;

import com.example.etl1.model.DistributionChannel;
import com.example.etl1.model.Location;
import com.example.etl1.model.ShippingLane;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DistributionChannelRepository extends CrudRepository<DistributionChannel, String> {
    Optional<DistributionChannel> findByShippingLane(ShippingLane shippingLane);

    Optional<DistributionChannel> findByWarehouseAndDistributionFacilityAndShippingLane(Location warehouse, Location distributionFacility, ShippingLane shippingLane);
}
