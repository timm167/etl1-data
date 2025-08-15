package com.example.etl1.model.components;

import org.junit.jupiter.api.Test;

public class TestMotherboard {
    @Test
    public void testMotherboardConstructs() {
        Motherboard motherboard = new Motherboard("Test name", 120.00, "Test socket", "Test form factor", 128, 4, "Test colour");

        assert motherboard.getId() == null;
        assert motherboard.getName().equals("Test name");
        assert motherboard.getPrice() == 120.00;
        assert motherboard.getSocket().equals("Test socket");
        assert motherboard.getForm_factor().equals("Test form factor");
        assert motherboard.getMax_memory() == 128;
        assert motherboard.getMemory_slots() == 4;
        assert motherboard.getColor().equals("Test colour");
    }
}
