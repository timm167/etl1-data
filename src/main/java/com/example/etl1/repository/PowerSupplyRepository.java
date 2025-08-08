package com.example.etl1.repository;

import com.example.etl1.model.PowerSupply;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PowerSupplyRepository extends CrudRepository<PowerSupply, Integer> {
    List<PowerSupply> findAll(Sort sort);
}
