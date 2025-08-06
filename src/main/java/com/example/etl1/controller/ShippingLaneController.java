package com.example.etl1.controller;

import com.example.etl1.model.GeoLocation;
import com.example.etl1.model.Shipper;
import com.example.etl1.model.ShippingLane;
import com.example.etl1.service.GeoLocationService;
import com.example.etl1.service.ShippingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/lane")
public class ShippingLaneController {

    private final GeoLocationService geoLocationService;
    private final ShippingService shippingService;

    public ShippingLaneController(GeoLocationService geoLocationService, ShippingService shippingService) {
        this.geoLocationService = geoLocationService;
        this.shippingService = shippingService;
    }

    @PostMapping("/closest")
    public ResponseEntity<ShippingLane> getClosestShippingLane(
            @RequestParam("from") String from,
            @RequestParam("to") String to) {

        GeoLocation originGeo = geoLocationService.getTopGeoLocation(from);
        GeoLocation destinationGeo = geoLocationService.getTopGeoLocation(to);

        if (originGeo == null || destinationGeo == null) {
            return ResponseEntity.badRequest().build();
        }

        Shipper originShipper = shippingService.findClosestShipper(
                Double.parseDouble(originGeo.getLat()),
                Double.parseDouble(originGeo.getLon())
        );

        Shipper destinationShipper = shippingService.findClosestShipper(
                Double.parseDouble(destinationGeo.getLat()),
                Double.parseDouble(destinationGeo.getLon())
        );

        ShippingLane lane = new ShippingLane();
        lane.setOriginPort(from);
        lane.setDestinationPort(to);
        lane.setOriginShipper(originShipper);
        lane.setDestinationShipper(destinationShipper);

        return ResponseEntity.ok(lane);
    }


}
