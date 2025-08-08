package com.example.etl1.repository.components;

import com.example.etl1.model.components.PowerSupply;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PowerSupplyRepository extends CrudRepository<PowerSupply, Integer> {
    List<PowerSupply> findAll(Sort sort);
}
