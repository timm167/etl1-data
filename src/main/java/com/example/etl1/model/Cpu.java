package com.example.etl1.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "CPUS")
public class Cpu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private double price;
    private Integer core_count;
    @JsonProperty("core_clock")
    private double coreClock;
    private double boost_clock;
    private String microarchitecture;
    private int tdp;
    private String graphics;

    public Cpu(String name, double price, Integer core_count, double core_clock, double boost_clock, String microarchitecture, int tdp, String graphics) {
        this.name = name;
        this.price = price;
        this.core_count = core_count;
        this.coreClock = core_clock;
        this.boost_clock = boost_clock;
        this.microarchitecture = microarchitecture;
        this.tdp = tdp;
        this.graphics = graphics;
    }
}

