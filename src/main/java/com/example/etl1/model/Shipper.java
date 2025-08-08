package com.example.etl1.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "shippers")
public class Shipper {
    private String contact_phone;
    private String contact_url;
    private String facility_name;
    private String formatted_address;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double lat;
    private Double lon;
    private String operating_hours;
    private String place_category;
}
