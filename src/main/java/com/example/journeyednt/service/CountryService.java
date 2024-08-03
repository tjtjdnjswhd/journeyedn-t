package com.example.journeyednt.service;

import com.example.journeyednt.domain.CountryDto;
import com.example.journeyednt.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;

    // cityId로 city에 해당하는 countries 가져오기
    @Transactional(readOnly = true)
    public List<CountryDto> getCountriesByCityId(Integer cityId) {
        return countryRepository.findCountriesByCityId(cityId).stream()
                .map(CountryDto::fromEntity)
                .collect(Collectors.toList());
    }

    // cityName으로 city에 해당하는 countries 이름 가져오기
    @Transactional(readOnly = true)
    public List<CountryDto> getCountriesNameByCityName(String cityName) {
        return countryRepository.findCountriesNameByCityName(cityName).stream()
                .map(CountryDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Map<String, Map<String, Integer>> getCityCountryMapping() {
        Map<String, Map<String, Integer>> result = new HashMap<>();
        // countryRepository 자체에서 새로운 타입으로 가져와 Mapping하는 방법
        List<Object[]> cityCountryList = countryRepository.findAllCityCountry();

        for (Object[] obj : cityCountryList) {
            String cityName = (String) obj[0];
            String countryName = (String) obj[1];
            Integer countryId = (Integer) obj[2];

            result.computeIfAbsent(cityName, k -> new HashMap<>()).put(countryName, countryId);
        }

        return result;
    }
}
