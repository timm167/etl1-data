package com.example.etl1.repository;

import com.example.etl1.model.Case;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CaseRepository extends CrudRepository<Case, Integer> {
    List<Case> findAll(Sort sort);
}
