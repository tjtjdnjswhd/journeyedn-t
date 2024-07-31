package com.example.journeyednt.service;

import com.example.journeyednt.domain.CityDto;
import com.example.journeyednt.domain.CountryDto;
import com.example.journeyednt.entity.City;
import com.example.journeyednt.entity.Country;
import com.example.journeyednt.repository.CityRepository;
import com.example.journeyednt.repository.CountryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService {
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    @Autowired
    public CityService(CityRepository cityRepository, CountryRepository countryRepository) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
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
    // Query 문으로 city에 대한 country 가져오기

//    @Transactional(readOnly = true)
//    public CityDto getCityWithCountries(Integer cityId) {
//        City city = cityRepository.findById(cityId)
//                .orElseThrow(() -> new EntityNotFoundException("City not found with id: " + cityId));
//
//        List<CountryDto> countries = city.getCountries().stream()
//                .map(CountryDto::fromEntity)
//                .collect(Collectors.toList());
//
//        return CityDto.builder()
//                .id(city.getId())
//                .name(city.getName())
//                .countries(countries)
//                .build();
//    }
//    // City 객체에 있는 countries로 가져오기



}
