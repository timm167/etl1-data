package com.example.etl1.repository;

import com.example.etl1.model.PreBuiltPC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrebuiltPCRepository extends JpaRepository<PreBuiltPC, Long> {
    Optional<PreBuiltPC> findByModelNumber(String modelNumber);
    @Query("SELECT p FROM PreBuiltPC p WHERE " +
            "p.gpu LIKE %:searchTerm% OR " +
            "p.cpu LIKE %:searchTerm% OR " +
            "p.memory LIKE %:searchTerm% OR " +
            "p.ssd LIKE %:searchTerm%")
    List<PreBuiltPC> findByComponentNames(@Param("searchTerm") String searchTerm);
}
