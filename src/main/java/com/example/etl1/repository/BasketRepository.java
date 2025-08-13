package com.example.etl1.repository;

import com.example.etl1.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {
    // Spring automatically generates the implementation
    // SELECT * FROM baskets WHERE session_id = ?
    Optional<Basket> findBySessionId(String sessionId);
}