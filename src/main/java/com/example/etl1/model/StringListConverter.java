package com.example.etl1.model;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.List;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        if (attribute == null) {
            return null;
        }
        return String.join("||", attribute);
    }
    @Override
    public List<String> convertToEntityAttribute(String data) {
        if (data == null || data.isEmpty()) {
            return List.of();
        }
        return Arrays.asList(data.split("\\|\\|"));
    }
}
