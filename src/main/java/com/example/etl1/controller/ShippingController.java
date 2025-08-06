package com.example.etl1.controller;
import com.example.etl1.model.GeoLocation;
import com.example.etl1.model.Shipper;
import com.example.etl1.service.GeoLocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.etl1.service.ShippingService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShippingController {

    private final GeoLocationService geoLocationService;
    private final ShippingService shippingService;

    public ShippingController(GeoLocationService geoLocationService, ShippingService shippingService) {
        this.geoLocationService = geoLocationService;
        this.shippingService = shippingService;
    }

    @GetMapping("/closest")
    public ResponseEntity<Shipper> getClosestShipper(@RequestParam String location) {
        GeoLocation geoLocation = geoLocationService.getTopGeoLocation(location);
        if (geoLocation == null) {
            return ResponseEntity.notFound().build();
        }

        Shipper closest = shippingService.findClosestShipper(
                Double.parseDouble(geoLocation.getLat()),
                Double.parseDouble(geoLocation.getLon())
        );

        if (closest == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(closest);
    }

}
