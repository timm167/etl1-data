package com.example.etl1.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

@Entity
@Table(name = "distribution_channels")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistributionChannel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", foreignKey = @ForeignKey(name = "fk_warehouse"))
    private Location warehouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "distribution_facility_id", foreignKey = @ForeignKey(name = "fk_distribution_facility"))
    private Location distributionFacility;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_lane_id", foreignKey = @ForeignKey(name = "fk_shipping_lane"))
    private ShippingLane shippingLane;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime updatedAt;
}

