package com.example.etl1.model.components;

import org.junit.jupiter.api.Test;

public class TestCase {
    @Test
    public void testCaseConstructs() {
        Case caseObject = new Case("Test name", 9.99, "Test type", "Test colour", "Test side panel", 50.0, 4);

        assert caseObject.getId() == null;
        assert caseObject.getName().equals("Test name");
        assert caseObject.getPrice() == 9.99;
        assert caseObject.getType().equals("Test type");
        assert caseObject.getColor().equals("Test colour");
        assert caseObject.getSide_panel().equals("Test side panel");
        assert caseObject.getExternalVolume() == 50.0;
        assert caseObject.getInternal_35_bays() == 4;
    }
}
