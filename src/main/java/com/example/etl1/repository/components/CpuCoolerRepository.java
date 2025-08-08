package com.example.etl1.repository.components;

import com.example.etl1.model.components.CpuCooler;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CpuCoolerRepository extends CrudRepository<CpuCooler, Integer> {
    List<CpuCooler> findAll(Sort sort);
}
