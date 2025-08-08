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
@Table(name = "MEMORY_SPEED")
public class MemorySpeed implements Comparable<MemorySpeed> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer speed;
    @ManyToOne
    @JoinColumn(name = "memory_id")
    private Memory memory;

    public MemorySpeed(Integer speed) {
        this.speed = speed;
    }

    @Override
    public int compareTo(MemorySpeed other) {
        return Integer.compare(this.speed, other.speed);
    }
}
