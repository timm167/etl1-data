package com.example.etl1.controller;

import com.example.etl1.model.logistics.DistributionChannel;
import com.example.etl1.model.logistics.Location;
import com.example.etl1.model.logistics.ShippingLane;
import com.example.etl1.repository.logistics.DistributionChannelRepository;
import org.springframework.stereotype.Service;

@Service
public class DistributionService {

    private final DistributionChannelRepository distributionChannelRepository;

    public DistributionService(DistributionChannelRepository distributionChannelRepository) {
        this.distributionChannelRepository = distributionChannelRepository;
    }

    public DistributionChannel createIfNotExists(String name, Location warehouse, Location distributionFacility, ShippingLane shippingLane) {
        return distributionChannelRepository.findByWarehouseAndDistributionFacilityAndShippingLane(warehouse, distributionFacility, shippingLane)
                .orElseGet(() -> {
                    DistributionChannel newChannel = new DistributionChannel();
                    newChannel.setName(name);
                    newChannel.setWarehouse(warehouse);
                    newChannel.setDistributionFacility(distributionFacility);
                    newChannel.setShippingLane(shippingLane);
                    newChannel.setActive(true);
                    return distributionChannelRepository.save(newChannel);
                });
    }
}
