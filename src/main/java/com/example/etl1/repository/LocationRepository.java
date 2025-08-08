package com.example.etl1.repository;

import com.example.etl1.model.Location;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LocationRepository extends CrudRepository<Location, Long> {
    List<Location> findByLocationType(Location.LocationTypeEnum locationType);

}
