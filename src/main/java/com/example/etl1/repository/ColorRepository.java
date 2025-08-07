package com.example.etl1.repository;

import com.example.etl1.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {
    boolean existsByName(String name);
}