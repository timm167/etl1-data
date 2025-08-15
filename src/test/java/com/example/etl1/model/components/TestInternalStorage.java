package com.example.etl1.model.components;

import org.junit.jupiter.api.Test;

public class TestInternalStorage {
    @Test
    public void testInternalStorageConstructs() {
        InternalStorage internalStorage = new InternalStorage("Test name", 150.50, 1000, 15.05, "Test type", 1500, "Test form factor");

        assert internalStorage.getId() == null;
        assert internalStorage.getName().equals("Test name");
        assert internalStorage.getPrice() == 150.50;
        assert internalStorage.getCapacity() == 1000;
        assert internalStorage.getPrice_per_gb() == 15.05;
        assert internalStorage.getType().equals("Test type");
        assert internalStorage.getCache() == 1500;
        assert internalStorage.getForm_factor().equals("Test form factor");

    }
}
