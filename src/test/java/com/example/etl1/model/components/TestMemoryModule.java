package com.example.etl1.model.components;

import org.junit.jupiter.api.Test;

public class TestMemoryModule {
    @Test
    public void testMemoryModulesConstructs() {
        MemoryModule memorySpeed = new MemoryModule(32);

        assert memorySpeed.getId() == null;
        assert memorySpeed.getModule() == 32;
        assert memorySpeed.getMemory() == null;
    }
}
