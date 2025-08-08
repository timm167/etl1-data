package com.example.etl1.repository.components;

import com.example.etl1.model.components.Case;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CaseRepository extends CrudRepository<Case, Integer> {
    List<Case> findAll(Sort sort);
}
