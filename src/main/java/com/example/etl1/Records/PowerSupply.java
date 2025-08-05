package com.example.etl1.Records;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PowerSupply(String name, double price, String type, String efficiency, int wattage, String modular, String color) { }
