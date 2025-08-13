package com.example.etl1.repository;

import com.example.etl1.model.Staff;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StaffRepository extends CrudRepository<Staff, Integer> {
    Optional<Staff> findByEmail(String email);
}
