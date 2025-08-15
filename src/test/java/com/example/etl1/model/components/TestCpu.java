package com.example.etl1.model.components;

import org.junit.jupiter.api.Test;

public class TestCpu {
    @Test
    public void testCpuConstructs() {
        Cpu cpu = new Cpu("Test name", 99.99, 6, 4.8, 5.0, "Test microarchitecture", 90, "Test integrated graphics");

        assert cpu.getId() == null;
        assert cpu.getName().equals("Test name");
        assert cpu.getPrice() == 99.99;
        assert cpu.getCore_count() == 6;
        assert cpu.getCoreClock() == 4.8;
        assert cpu.getBoost_clock() == 5.0;
        assert cpu.getMicroarchitecture().equals("Test microarchitecture");
        assert cpu.getTdp() == 90;
        assert cpu.getGraphics().equals("Test integrated graphics");
    }
}
