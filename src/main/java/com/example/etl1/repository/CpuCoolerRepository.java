package com.example.etl1.repository;

import com.example.etl1.model.CpuCooler;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CpuCoolerRepository extends CrudRepository<CpuCooler, Integer> {
    List<CpuCooler> findAll(Sort sort);
}
