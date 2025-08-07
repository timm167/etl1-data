package com.example.etl1.repository;

import com.example.etl1.model.Color;
import org.springframework.data.repository.CrudRepository;


public interface ColorRepository extends CrudRepository<Color, Integer> {
}
