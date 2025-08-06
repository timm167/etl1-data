package com.example.etl1.model;

import lombok.Data;

@Data
public class GeoLocation {
    private String lat;
    private String lon;
    private String name;
    private String display_name;
}
