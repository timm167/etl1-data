package com.example.etl1.model.components;

import org.junit.jupiter.api.Test;

public class TestGraphicsCard {
    @Test
    public void testGraphicsCardConstructs() {
        GraphicsCard graphicsCard = new GraphicsCard("Test name", 99.99, "Test chipset", 8.0, 1000, 1200, "Test colour", 500);

        assert graphicsCard.getId() == null;
        assert graphicsCard.getName().equals("Test name");
        assert graphicsCard.getPrice() == 99.99;
        assert graphicsCard.getChipset().equals("Test chipset");
        assert graphicsCard.getMemory() == 8.0;
        assert graphicsCard.getCoreClock() == 1000;
        assert graphicsCard.getBoost_clock() == 1200;
        assert graphicsCard.getColor().equals("Test colour");
        assert graphicsCard.getLength() == 500;
    }
}
