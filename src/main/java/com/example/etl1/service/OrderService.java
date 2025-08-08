package com.example.etl1.service;

import com.example.etl1.controller.DistributionService;
import com.example.etl1.model.*;
import com.example.etl1.repository.LocationRepository;
import com.example.etl1.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final LocationRepository locationRepository;
    private final ProductService productService;
    private final GeoLocationService geoLocationService;
    private final ShippingService shippingService;
    private final DistributionService distributionService;


    public OrderService(OrderRepository orderRepository, LocationRepository locationRepository, DistributionService distributionService, ProductService productService, GeoLocationService geoLocationService, ShippingService shippingService) {
        this.orderRepository = orderRepository;
        this.locationRepository = locationRepository;
        this.productService = productService;
        this.geoLocationService = geoLocationService;
        this.shippingService = shippingService;
        this.distributionService = distributionService;
    }

    public void createOrder(String address, Integer productId, Integer quantity) {
        Product product = productService.getProductById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Invalid product ID");
        }

        GeoLocation geoLocation = geoLocationService.getTopGeoLocation(address);

        double lat = Double.parseDouble(geoLocation.getLat());
        double lon = Double.parseDouble(geoLocation.getLon());

        Location closestDistributionFacility = findClosestByType(lat, lon, Location.LocationTypeEnum.DISTRIBUTION_FACILITY);

        Location closestWarehouse = findClosestByType(
                closestDistributionFacility.getLat().doubleValue(),
                closestDistributionFacility.getLon().doubleValue(),
                Location.LocationTypeEnum.WAREHOUSE);

        Shipper closestShipper = shippingService.findClosestShipper(
                closestDistributionFacility.getLat().doubleValue(),
                closestDistributionFacility.getLon().doubleValue());


        // Notes to self (Tim)
        // distributionService.createIfNotExists()
        // Needs data for create if not exists
        // requires shipping lane to be made first
        // build shipping lane service for this
        // extract distance logic to dry code
        // remove logic from here to put into distribution service i.e. closest shipper/warehouse


        Order order = new Order();
        order.setProduct(product);
        order.setQuantity(quantity);

        BigDecimal value = product.getPrice().multiply(BigDecimal.valueOf(quantity));
        order.setValue(value);

        order.setOrderTime(LocalDateTime.now());

        order.setExpectedDeliveryTime(order.getOrderTime().plusDays(15));

        order.setIsOpen(true);

        orderRepository.save(order);
    }

    public double distance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    public Location findClosestByType(double userLat, double userLon, Location.LocationTypeEnum type) {
        List<Location> locations = locationRepository.findByLocationType(type);
        Location closest = null;
        double minDistance = Double.MAX_VALUE;

        for (Location loc : locations) {
            double dist = distance(userLat, userLon, loc.getLat().doubleValue(), loc.getLon().doubleValue());
            if (dist < minDistance) {
                minDistance = dist;
                closest = loc;
            }
        }

        return closest;
    }
}
