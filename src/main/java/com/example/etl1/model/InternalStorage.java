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
@Table(name = "INTERNAL_STORAGE")
public class InternalStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private double price;
    private int capacity;
    private double price_per_gb;
    private String type;
    private int cache;
    private String form_factor;

    public InternalStorage(String name, double price, int capacity, double price_per_gb, String type, int cache, String form_factor) {
        this.name = name;
        this.price = price;
        this.capacity = capacity;
        this.price_per_gb = price_per_gb;
        this.type = type;
        this.cache = cache;
        this.form_factor = form_factor;
    }
}
