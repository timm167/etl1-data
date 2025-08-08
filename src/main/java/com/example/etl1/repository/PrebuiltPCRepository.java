package com.example.etl1.repository;

import com.example.etl1.model.PreBuiltPC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PrebuiltPCRepository extends JpaRepository<PreBuiltPC, Long> {
    Optional<PreBuiltPC> findByModelNumber(String modelNumber);
}
