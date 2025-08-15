package com.example.etl1.model.components;

import org.junit.jupiter.api.Test;

public class TestFanNoiseLevel {
    @Test
    public void testFanNoiseLevelConstructs() {
        FanNoiseLevel fanNoiseLevel = new FanNoiseLevel(10.5);

        assert fanNoiseLevel.getId() == null;
        assert fanNoiseLevel.getNoise_level() == 10.5;
        assert fanNoiseLevel.getCpu_cooler() == null;
    }
}
