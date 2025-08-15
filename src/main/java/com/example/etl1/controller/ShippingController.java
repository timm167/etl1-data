package com.example.etl1.controller;
import com.example.etl1.model.logistics.GeoLocation;
import com.example.etl1.model.logistics.Shipper;
import com.example.etl1.repository.logistics.ShipperRepository;
import com.example.etl1.service.GeoLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.etl1.service.ShippingService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ShippingController {

    @Autowired
    ShipperRepository shipperRepository;

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
            geoLocation.getLat().doubleValue(), geoLocation.getLon().doubleValue()
        );

        if (closest == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(closest);
    }

    @GetMapping("/shipper/{id}")
    public ModelAndView showShipperDetails(@PathVariable Integer id) {
        Shipper shipper = shipperRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shipper not found"));

        ModelAndView mav = new ModelAndView("shipper_details");
        mav.addObject("shipper", shipper);
        return mav;
    }


}
