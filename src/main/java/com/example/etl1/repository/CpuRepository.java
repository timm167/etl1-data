package com.example.etl1.repository;

import com.example.etl1.model.Cpu;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CpuRepository extends CrudRepository<Cpu, Integer> {
    List<Cpu> findByNameContaining(String name);
}
