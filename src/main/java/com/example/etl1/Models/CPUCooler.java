package com.example.etl1.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "CPU_COOLERS")
public class CPUCooler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private double price;
    @OneToMany(mappedBy = "cpu_cooler")
    private List<FanRPM> rpm;
    @OneToMany(mappedBy = "cpu_cooler")
    private List<FanNoiseLevel> noise_level;
    private String color;
    private int size;

    public CPUCooler(String name, double price, List<FanRPM> rpm, List<FanNoiseLevel> noise_level, String color, int size) {
        this.name = name;
        this.price = price;
        this.rpm = rpm;
        this.noise_level = noise_level;
        this.color = color;
        this.size = size;
    }
}