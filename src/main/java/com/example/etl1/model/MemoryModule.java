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
@Table(name = "MEMORY_MODULES")
public class MemoryModule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer module;
    @ManyToOne
    @JoinColumn(name = "memory_id")
    private Memory memory;

    public MemoryModule(Integer module) {
        this.module = module;
    }
}
