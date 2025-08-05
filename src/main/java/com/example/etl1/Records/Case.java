package com.example.etl1.Records;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Case(String name, double price, String type, String color, String side_panel, double external_volume, int internal_35_bays) { }

