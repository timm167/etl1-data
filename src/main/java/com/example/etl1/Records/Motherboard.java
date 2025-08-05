package com.example.etl1.Records;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Motherboard(String name, double price, String socket, String form_factor, int max_memory, int memory_slots, String color) { }
