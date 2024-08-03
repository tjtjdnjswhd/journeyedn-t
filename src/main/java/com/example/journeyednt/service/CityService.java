package com.example.journeyednt.service;

import com.example.journeyednt.domain.CityDto;
import com.example.journeyednt.domain.CountryDto;
import com.example.journeyednt.entity.City;
import com.example.journeyednt.repository.CityRepository;
import com.example.journeyednt.repository.CountryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    public List<CityDto> findAll() {
        return cityRepository.findAll().stream()
                .map(CityDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CityDto getCityWithCountriesByQuery(Integer cityId) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new EntityNotFoundException("City not found with id: " + cityId));

        List<CountryDto> countries = countryRepository.findCountriesByCityId(cityId).stream()
                .map(CountryDto::fromEntity)
                .collect(Collectors.toList());

        return CityDto.builder()
                .id(city.getId())
                .name(city.getName())
                .countries(countries)
                .build();
    }
}
