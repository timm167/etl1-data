package com.example.etl1.repository.components;

import com.example.etl1.model.components.Memory;

import java.util.List;

public interface MemoryRepository extends ComponentRepository<Memory> {
    List<Memory> findByNameContaining(String name);
}
