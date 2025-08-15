package com.example.etl1.repository.logistics;

import com.example.etl1.model.logistics.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findAll();

    List<Order> findByUserId(Integer userId);

    @Query("SELECT o FROM Order o " +
            "JOIN FETCH o.distributionChannel dc " +
            "JOIN FETCH dc.warehouse " +
            "JOIN FETCH dc.distributionFacility " +
            "JOIN FETCH dc.startShipper " +
            "JOIN FETCH dc.endShipper")
    List<Order> findAllWithDistribution();

}
