package com.example.etl1.repository;

import com.example.etl1.model.GraphicsCard;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface GraphicsCardRepository extends CrudRepository<GraphicsCard, Integer> {
    List<GraphicsCard> findByChipsetContaining(String chipset);
    List<GraphicsCard> findAll(Sort sort);
}
