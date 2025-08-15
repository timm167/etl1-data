package com.example.etl1.repository;

import com.example.etl1.model.Basket;
import com.example.etl1.model.BasketItem;
import com.example.etl1.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BasketItemRepository extends JpaRepository<BasketItem, Long> {
    // Custom method to find a specific product in a specific basket
    // SELECT * FROM basket_items WHERE basket_id = x AND product_id = x
    Optional<BasketItem> findByBasketAndProduct(Basket basket, Product product);
}