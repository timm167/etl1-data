package com.example.etl1.service;

import com.example.etl1.model.logistics.DistributionChannel;
import com.example.etl1.model.logistics.Location;
import com.example.etl1.model.logistics.Shipper;
import com.example.etl1.repository.logistics.DistributionChannelRepository;
import org.springframework.stereotype.Service;

@Service
public class DistributionService {

    private final DistributionChannelRepository distributionChannelRepository;

    public DistributionService(DistributionChannelRepository distributionChannelRepository) {
        this.distributionChannelRepository = distributionChannelRepository;
    }

    public DistributionChannel createIfNotExists(String name, Location warehouse, Location distributionFacility, Shipper startShipper, Shipper endShipper) {
        return distributionChannelRepository.findByWarehouseAndDistributionFacilityAndStartShipperAndEndShipper(warehouse, distributionFacility, startShipper, endShipper)
                .orElseGet(() -> {
                    DistributionChannel newChannel = new DistributionChannel();
                    newChannel.setName(name);
                    newChannel.setWarehouse(warehouse);
                    newChannel.setDistributionFacility(distributionFacility);
                    newChannel.setStartShipper(startShipper);
                    newChannel.setEndShipper(endShipper);
                    newChannel.setActive(true);
                    return distributionChannelRepository.save(newChannel);
                });
    }
}
