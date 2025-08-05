package com.example.etl1.Records;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CPU(String name, double price, Integer core_count, double core_clock, double boost_clock, String microarchitecture, int tdp, String graphics) { }

