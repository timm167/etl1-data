package com.example.etl1.service;

import com.example.etl1.model.*;
import com.example.etl1.model.logistics.*;
import com.example.etl1.repository.logistics.LocationRepository;
import com.example.etl1.repository.logistics.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

        GeoLocation deliveryLocation = geoLocationService.getTopGeoLocation(address);

        Location closestDistributionFacility = findClosestByType(
                deliveryLocation.getLat().doubleValue(),
                deliveryLocation.getLon().doubleValue(),
                Location.LocationTypeEnum.DISTRIBUTION_FACILITY);

        Location closestWarehouse = findClosestByType(
                closestDistributionFacility.getLat().doubleValue(),
                closestDistributionFacility.getLon().doubleValue(),
                Location.LocationTypeEnum.WAREHOUSE);

        Shipper closestStartShipper = shippingService.findClosestShipper(
                closestDistributionFacility.getLat().doubleValue(),
                closestDistributionFacility.getLon().doubleValue());

        Shipper closestEndShipper = shippingService.findClosestShipper(
                deliveryLocation.getLat().doubleValue(),
                deliveryLocation.getLon().doubleValue());

        String channelName = formatRoute(closestStartShipper, closestEndShipper);

        DistributionChannel distributionChannel = distributionService.createIfNotExists(
                channelName, closestWarehouse, closestDistributionFacility, closestStartShipper, closestEndShipper);

        Order order = new Order();
        order.setProduct(product);
        order.setQuantity(quantity);

        BigDecimal value = product.getPrice().multiply(BigDecimal.valueOf(quantity));
        order.setValue(value);

        order.setDistributionChannel(distributionChannel);

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
        List<Location> locations = locationRepository.findByLocationType(type.name());
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

    public static String formatRoute(Shipper start, Shipper end) {
        return String.format("%.6f, %.6f - %.6f, %.6f",
                start.getLat(), start.getLon(),
                end.getLat(), end.getLon());
    }
}
