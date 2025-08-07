package com.example.etl1.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "POWER_SUPPLIES")
public class PowerSupply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private double price;
    private String type;
    private String efficiency;
    private int wattage;
    private String modular;
    private String color;

    public PowerSupply(String name, double price, String type, String efficiency, int wattage, String modular, String color) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.efficiency = efficiency;
        this.wattage = wattage;
        this.modular = modular;
        this.color = color;
    }
}
