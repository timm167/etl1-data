package com.example.etl1.Records;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GraphicsCard(String name, double price, String chipset, int memory, int core_clock, int boost_clock, String color, int length) { }
