package com.example.etl1.model;

import com.example.etl1.model.Shipper;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "shipping_lanes")
public class ShippingLane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originPort;
    private String destinationPort;

    @ManyToOne
    @JoinColumn(name = "origin_shipper_id")
    private Shipper originShipper;

    @ManyToOne
    @JoinColumn(name = "destination_shipper_id")
    private Shipper destinationShipper;

}