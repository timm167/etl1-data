package com.example.etl1.Repositories;

import com.example.etl1.Records.Case;
import org.springframework.data.repository.CrudRepository;

public interface CaseRepository extends CrudRepository<Case, Integer> {
}
