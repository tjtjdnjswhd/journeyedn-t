package com.example.journeyednt.service;

import com.example.journeyednt.domain.CountryDto;
import com.example.journeyednt.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryService {
    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Transactional(readOnly = true)
    public List<CountryDto> getCountriesByCityId(Integer cityId) {
        return countryRepository.findCountriesByCityId(cityId).stream()
                .map(CountryDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<CountryDto> getCountriesNameByCityName(String cityName) {
        return countryRepository.findCountriesNameByCityName(cityName).stream()
                .map(CountryDto::fromEntity)
                .collect(Collectors.toList());
    }


}
