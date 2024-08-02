package com.example.journeyednt.controller;

import com.example.journeyednt.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/country")
public class CountryController {
    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    // City 이름, Country 이름과 아이디를 Map으로 반환하는 api
    @GetMapping
    public ResponseEntity<Map<String, Map<String, Integer>>> getAllCountries() {
        Map<String, Map<String, Integer>> cityCountries = countryService.getCityCountryMapping();
        return ResponseEntity.ok(cityCountries);
    }

}
