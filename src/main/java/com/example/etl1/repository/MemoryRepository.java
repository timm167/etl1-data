package com.example.etl1.repository;

import com.example.etl1.model.Memory;
import org.springframework.data.repository.CrudRepository;

public interface MemoryRepository extends CrudRepository<Memory, Integer> { }
