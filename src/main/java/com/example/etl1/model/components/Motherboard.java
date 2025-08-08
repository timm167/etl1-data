package com.example.etl1.model.components;

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
@Table(name = "MOTHERBOARDS")
public class Motherboard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private double price;
    private String socket;
    private String form_factor;
    private int max_memory;
    private int memory_slots;
    private String color;

    public Motherboard(String name, double price, String socket, String form_factor, int max_memory, int memory_slots, String color) {
        this.name = name;
        this.price = price;
        this.socket = socket;
        this.form_factor = form_factor;
        this.max_memory = max_memory;
        this.memory_slots = memory_slots;
        this.color = color;
    }
}
