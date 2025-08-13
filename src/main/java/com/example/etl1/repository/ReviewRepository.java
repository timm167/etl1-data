// java
package com.example.etl1.repository;

import com.example.etl1.model.Review;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByProductId(Integer productId, Sort sort);
    boolean existsByProductIdAndEmail(Integer productId, String email);
}
