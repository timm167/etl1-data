package com.example.etl1.Records;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Memory(String name, double price, List<Integer> speed, List<Integer> modules, double price_per_gb, String color, int first_word_latency, int cas_latency) { }
