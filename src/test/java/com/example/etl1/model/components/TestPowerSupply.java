package com.example.etl1.model.components;

import org.junit.jupiter.api.Test;

public class TestPowerSupply {
    @Test
    public void testPowerSupplyConstructs() {
        PowerSupply powerSupply = new PowerSupply("Test name", 249.99, "Test type", "Test efficiency", 900, "Test modular", "Test colour");

        assert powerSupply.getId() == null;
        assert powerSupply.getName().equals("Test name");
        assert powerSupply.getPrice() == 249.99;
        assert powerSupply.getType().equals("Test type");
        assert powerSupply.getEfficiency().equals("Test efficiency");
        assert powerSupply.getWattage() == 900;
        assert powerSupply.getModular().equals("Test modular");
        assert powerSupply.getColor().equals("Test colour");
    }
}
