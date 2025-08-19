package com.example.etl1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderFormDto {
    private String fullName;
    private String address1;
    private String address2; // optional
    private String city;
    private String postcode;
    private String country;
    private Integer productId;
    private Integer quantity;

    public String getFullAddress() {
        StringBuilder sb = new StringBuilder();
        sb.append(address1);

        if (address2 != null && !address2.isBlank()) {
            sb.append(", ").append(address2);
        }

        sb.append(", ").append(city);
        sb.append(", ").append(postcode);
        sb.append(", ").append(country);

        return sb.toString();
    }
}