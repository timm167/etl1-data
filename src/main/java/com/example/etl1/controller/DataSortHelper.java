package com.example.etl1.controller;

import org.springframework.data.domain.Sort;

public class DataSortHelper {
    public static Sort getSortMethod(String sortBy, String order) {
        Sort.Direction direction;

        if (sortBy != null) {
            if (order != null && order.equals("Descending")) {
                direction = Sort.Direction.DESC;
            } else {
                direction = Sort.Direction.ASC;
            }

            String property = switch (sortBy) {
                case "Name" -> "name";
                case "Price" -> "price";
                case "Size" -> "externalVolume";
                case "Core Clock" -> "coreClock";
                case "Capacity" -> "capacity";
                case "Speed" -> "speed.speed";
                case "Wattage" -> "wattage";
                default -> null;
            };

            if (property != null) {
                return Sort.by(direction, property);
            }
        }

        return null;
    }
}
