package com.example.etl1.service;

import com.example.etl1.model.GeoLocation;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class GeoLocationService {

    private final RestTemplate restTemplate;

    public GeoLocationService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public GeoLocation getTopGeoLocation(String placeName) {
        URI uri = UriComponentsBuilder.fromUriString("https://nominatim.openstreetmap.org/search")
                .queryParam("q", placeName)
                .queryParam("format", "json")
                .build()
                .encode()
                .toUri();

        ResponseEntity<GeoLocation[]> response = restTemplate.getForEntity(uri, GeoLocation[].class);
        GeoLocation[] locations = response.getBody();

        if (locations != null && locations.length > 0) {
            return locations[0];
        } else {
            return null;
        }
    }
}

