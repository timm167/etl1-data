package com.example.etl1.repository.components;

import com.example.etl1.model.components.Memory;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MemoryRepository extends CrudRepository<Memory, Integer> {
    List<Memory> findAll(Sort sort);
}
