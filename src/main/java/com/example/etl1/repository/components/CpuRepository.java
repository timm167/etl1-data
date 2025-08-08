package com.example.etl1.repository.components;

import com.example.etl1.model.components.Cpu;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CpuRepository extends CrudRepository<Cpu, Integer> {
    List<Cpu> findByNameContaining(String name);
}
