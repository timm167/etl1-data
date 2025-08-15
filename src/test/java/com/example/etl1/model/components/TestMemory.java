package com.example.etl1.model.components;

import org.junit.jupiter.api.Test;

public class TestMemory {
    @Test
    public void testMemoryConstructs() {
        Memory memory = new Memory("Test name", 120.99, null, null, 10.99, "Test colour", 20.5, 10);

        assert memory.getId() == null;
        assert memory.getName().equals("Test name");
        assert memory.getPrice() == 120.99;
        assert memory.getSpeed() == null;
        assert memory.getModules() == null;
        assert memory.getPrice_per_gb() == 10.99;
        assert memory.getColor().equals("Test colour");
        assert memory.getFirst_word_latency() == 20.5;
        assert memory.getCas_latency() == 10;
    }
}
