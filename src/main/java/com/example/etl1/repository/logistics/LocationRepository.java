package com.example.etl1.repository.logistics;

import com.example.etl1.model.logistics.Location;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LocationRepository extends CrudRepository<Location, Long> {
    List<Location> findByLocationType(Location.LocationTypeEnum locationType);

}
