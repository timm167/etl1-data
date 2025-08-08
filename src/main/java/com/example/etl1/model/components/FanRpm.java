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
@Table(name = "FAN_RPM")
public class FanRpm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer rpm;
    @ManyToOne
    @JoinColumn(name = "cpu_cooler_id")
    private CpuCooler cpu_cooler;

    public FanRpm(Integer rpm) {
        this.rpm = rpm;
    }
}
