package com.example.etl1.model.logistics;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GeoLocation {
    private BigDecimal lat;
    private BigDecimal lon;
    private String name;
    private String display_name;
}
