package com.example.etl1.repository.components;

import com.example.etl1.model.components.InternalStorage;

import java.util.List;

public interface InternalStorageRepository extends ComponentRepository<InternalStorage> {
    List<InternalStorage> findByCapacity(int capacity);
}
