package com.example.etl1.Repositories;

import com.example.etl1.model.PreBuiltPC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PrebuiltPCRepository extends JpaRepository<PreBuiltPC, Long> {
}
