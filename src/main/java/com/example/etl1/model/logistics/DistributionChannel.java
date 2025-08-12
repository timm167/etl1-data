package com.example.etl1.model.logistics;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

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
    @JoinColumn(name = "start_shipper_id", foreignKey = @ForeignKey(name = "fk_shipper"))
    private Shipper startShipper;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "end_shipper_id", foreignKey = @ForeignKey(name = "fk_shipper"))
    private Shipper endShipper;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime updatedAt;
}

