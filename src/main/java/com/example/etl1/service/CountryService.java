package com.example.etl1.service;

import com.example.etl1.dto.CountryDto;
import org.springframework.stereotype.Service;

import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CountryService {

    private final List<CountryDto> countries;

    public CountryService() {
        Locale display = Locale.ENGLISH;
        Collator collator = Collator.getInstance(display);
        collator.setStrength(Collator.PRIMARY);

        List<CountryDto> list = Arrays.stream(Locale.getISOCountries())
                .map(code -> {
                    Locale loc = new Locale("", code);
                    return new CountryDto(code, loc.getDisplayCountry(display));
                })
                .sorted(Comparator.comparing(CountryDto::getName, collator))
                .collect(Collectors.toList());

        this.countries = Collections.unmodifiableList(list);
    }

    public List<CountryDto> getAll() {
        return countries;
    }
}