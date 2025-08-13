package com.example.etl1.repository.components;

import com.example.etl1.model.components.Cpu;
import java.util.List;

public interface CpuRepository extends ComponentRepository<Cpu> {
    List<Cpu> findByNameContaining(String name);
}
