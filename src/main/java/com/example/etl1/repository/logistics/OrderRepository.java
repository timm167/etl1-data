package com.example.etl1.repository.logistics;

import com.example.etl1.model.logistics.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
