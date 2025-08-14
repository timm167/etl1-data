package com.example.etl1.repository;

import com.example.etl1.model.Product;  // Changed from entity to model
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {  // Changed Long to Integer (model.Product uses Integer id)
    List<Product> findAll(Sort sort);
}