package com.example.etl1.model.components;

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
@Table(name = "GRAPHICS_CARDS")
public class GraphicsCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private double price;
    private String chipset;
    private double memory;
    @JsonProperty("core_clock")
    private int coreClock;
    private int boost_clock;
    private String color;
    private int length;

    public GraphicsCard(String name, double price, String chipset, double memory, int core_clock, int boost_clock, String color, int length) {
        this.name = name;
        this.price = price;
        this.chipset = chipset;
        this.memory = memory;
        this.coreClock = core_clock;
        this.boost_clock = boost_clock;
        this.color = color;
        this.length = length;
    }
}
