package com.example.etl1.repository.logistics;

import com.example.etl1.model.logistics.DistributionChannel;
import com.example.etl1.model.logistics.Location;
import com.example.etl1.model.logistics.Shipper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DistributionChannelRepository extends CrudRepository<DistributionChannel, String> {

    Optional<DistributionChannel> findByWarehouseAndDistributionFacilityAndStartShipperAndEndShipper(Location warehouse, Location distributionFacility, Shipper startShipper, Shipper endShipper);
//    @Query("SELECT dc FROM DistributionChannel dc " +
//            "JOIN FETCH dc.warehouse " +
//            "JOIN FETCH dc.distributionFacility " +
//            "JOIN FETCH dc.startShipper " +
//            "JOIN FETCH dc.endShipper ")
//    List<DistributionChannel> findAllStaffView();

}
