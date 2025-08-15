package com.example.etl1.model.components;

import org.junit.jupiter.api.Test;

public class TestFanRpm {
    @Test
    public void testFanRpmConstructs() {
        FanRpm fanRpm = new FanRpm(500);

        assert fanRpm.getId() == null;
        assert fanRpm.getRpm() == 500;
        assert fanRpm.getCpu_cooler() == null;
    }


}
