package com.example.etl1.repository;

import com.example.etl1.model.InternalStorage;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InternalStorageRepository extends CrudRepository<InternalStorage, Integer> {
    List<InternalStorage> findAll(Sort sort);
}
