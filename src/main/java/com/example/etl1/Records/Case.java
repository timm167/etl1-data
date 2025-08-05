package com.example.etl1.Records;

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
@Table(name = "CASES")
public class Case {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private double price;
    private String type;
    private String color;
    private String side_panel;
    private double external_volume;
    private int internal_35_bays;

    public Case(String name, double price, String type, String color, String side_panel, double external_volume, int internal_35_bays) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.color = color;
        this.side_panel = side_panel;
        this.external_volume = external_volume;
        this.internal_35_bays = internal_35_bays;
    }
}
