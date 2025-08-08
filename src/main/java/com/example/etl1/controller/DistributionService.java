package com.example.etl1.controller;

import com.example.etl1.model.DistributionChannel;
import com.example.etl1.model.Location;
import com.example.etl1.model.ShippingLane;
import com.example.etl1.repository.DistributionChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
