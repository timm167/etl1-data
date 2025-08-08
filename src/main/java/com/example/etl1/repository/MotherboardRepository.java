package com.example.etl1.repository;

import com.example.etl1.model.Motherboard;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MotherboardRepository extends CrudRepository<Motherboard, Integer> {
    List<Motherboard> findAll(Sort sort);
}
