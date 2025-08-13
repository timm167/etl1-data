package com.example.etl1.repository.components;

import com.example.etl1.model.components.Component;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ComponentRepository<T extends Component> extends CrudRepository<T, Integer> {
    List<T> findAll(Sort sort);
}
