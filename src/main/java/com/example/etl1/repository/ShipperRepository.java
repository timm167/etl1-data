package com.example.etl1.repository;

import com.example.etl1.model.Shipper;
import org.springframework.data.repository.CrudRepository;

public interface ShipperRepository extends CrudRepository<Shipper, String> {

}