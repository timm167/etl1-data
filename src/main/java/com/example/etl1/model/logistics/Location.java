package com.example.etl1.model.logistics;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "locations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    public enum LocationTypeEnum {
        DISTRIBUTION_FACILITY,
        WAREHOUSE,
        FACTORY,
        OFFICE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "location_type", nullable = false)
    private LocationTypeEnum locationType;

    @Column(name = "location_name", length = 100, nullable = false)
    private String locationName;

    @Column(nullable = false, length = 100)
    private String country;

    @Column(precision = 9, scale = 6)
    private BigDecimal lat;

    @Column(precision = 9, scale = 6)
    private BigDecimal lon;

}
