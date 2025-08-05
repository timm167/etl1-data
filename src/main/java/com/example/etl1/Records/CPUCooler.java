package com.example.etl1.Records;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public record CPUCooler(String name, double price, List<Integer> rpm, List<Double> noise_level, String color, int size) { }
