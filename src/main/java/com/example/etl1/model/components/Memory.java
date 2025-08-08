package com.example.etl1.model.components;

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
@Table(name = "MEMORY")
public class Memory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private double price;
    @OneToMany(mappedBy = "memory")
    private List<MemorySpeed> speed;
    @OneToMany(mappedBy = "memory")
    private List<MemoryModule> modules;
    private double price_per_gb;
    private String color;
    private double first_word_latency;
    private int cas_latency;

    public Memory(String name, double price, List<MemorySpeed> speed, List<MemoryModule> modules, double price_per_gb, String color, double first_word_latency, int cas_latency) {
        this.name = name;
        this.price = price;
        this.speed = speed;
        this.modules = modules;
        this.price_per_gb = price_per_gb;
        this.color = color;
        this.first_word_latency = first_word_latency;
        this.cas_latency = cas_latency;
    }
}
