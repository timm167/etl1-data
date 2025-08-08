package com.example.etl1.repository;
import com.example.etl1.model.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    List<Product> findAll(Sort sort);

}
