package com.example.etl1.model.components;

import org.junit.jupiter.api.Test;

public class TestCpuCooler {
    @Test
    public void testCpuCoolerConstructs() {
        CpuCooler cpuCooler = new CpuCooler("Test name", 99.99, null, null, "Test colour", 50);

        assert cpuCooler.getId() == null;
        assert cpuCooler.getName().equals("Test name");
        assert cpuCooler.getPrice() == 99.99;
        assert cpuCooler.getRpm() == null;
        assert cpuCooler.getNoise_level() == null;
        assert cpuCooler.getColor().equals("Test colour");
        assert cpuCooler.getSize() == 50;
    }
}
