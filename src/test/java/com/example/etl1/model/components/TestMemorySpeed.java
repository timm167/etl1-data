package com.example.etl1.model.components;

import org.junit.jupiter.api.Test;

public class TestMemorySpeed {
    @Test
    public void testMemorySpeedConstructs() {
        MemorySpeed memorySpeed = new MemorySpeed(2333);

        assert memorySpeed.getId() == null;
        assert memorySpeed.getSpeed() == 2333;
        assert memorySpeed.getMemory() == null;
    }
}
