package com.example.etl1.repository.logistics;

import com.example.etl1.model.logistics.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findAll();

    List<Order> findByUserId(Integer userId);
}
