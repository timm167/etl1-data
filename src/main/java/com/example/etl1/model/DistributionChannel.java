package com.example.etl1.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "distribution_channels")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistributionChannel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = "warehouse_id")
    private Integer warehouseId;

    @Column(name = "distribution_facility_id")
    private Integer distributionFacilityId;

    @Column(name = "shipping_lane_id")
    private Integer shippingLaneId;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime updatedAt;
}

