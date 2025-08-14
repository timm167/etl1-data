package com.example.etl1.repository.components;

import com.example.etl1.model.components.GraphicsCard;
import java.util.List;

public interface GraphicsCardRepository extends ComponentRepository<GraphicsCard> {
    List<GraphicsCard> findByChipsetContaining(String chipset);
}
