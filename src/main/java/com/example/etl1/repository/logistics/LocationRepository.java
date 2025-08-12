package com.example.etl1.repository.logistics;

import com.example.etl1.model.logistics.Location;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocationRepository extends CrudRepository<Location, Long> {
    @Query(value = "SELECT * FROM locations WHERE location_type = CAST(:type AS location_type_enum)", nativeQuery = true)
    List<Location> findByLocationType(@Param("type") String type);

}
