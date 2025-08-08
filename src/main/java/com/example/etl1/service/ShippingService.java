package com.example.etl1.service;

import com.example.etl1.model.logistics.Shipper;
import com.example.etl1.repository.logistics.ShipperRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ShippingService {

    private final ShipperRepository shipperRepository;

    public ShippingService(ShipperRepository shipperRepository) {
        this.shipperRepository = shipperRepository;
    }

    public Shipper findClosestShipper(double targetLat, double targetLon) {
        Iterable<Shipper> iterableShippers = shipperRepository.findAll();
        List<Shipper> allShippers = new ArrayList<>();
        iterableShippers.forEach(allShippers::add);

        return allShippers.stream()
                .filter(s -> s.getLat() != null && s.getLon() != null)
                .min(Comparator.comparingDouble(s -> haversine(targetLat, targetLon, s.getLat(), s.getLon())))
                .orElse(null);
    }


    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}

