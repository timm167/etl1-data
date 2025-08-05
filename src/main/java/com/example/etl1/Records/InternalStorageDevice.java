package com.example.etl1.Records;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record InternalStorageDevice(String name, double price, int capacity, double price_per_gb, String type, int cache, String form_factor) { }
