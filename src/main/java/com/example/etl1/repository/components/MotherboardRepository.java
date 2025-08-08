package com.example.etl1.repository.components;

import com.example.etl1.model.components.Motherboard;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MotherboardRepository extends CrudRepository<Motherboard, Integer> {
    List<Motherboard> findAll(Sort sort);
}
