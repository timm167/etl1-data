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
@Table(name = "FAN_NOISE_LEVEL")
public class FanNoiseLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double noise_level;
    @ManyToOne
    @JoinColumn(name = "cpu_cooler_id")
    private CpuCooler cpu_cooler;

    public FanNoiseLevel(Double noise_level) {
        this.noise_level = noise_level;
    }
}
