package com.example.journeyednt.domain;

import com.example.journeyednt.entity.City;
import com.example.journeyednt.entity.User;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CityDto {

    private Integer id;
    private String name;
    private List<CountryDto> countries;

    public static CityDto fromEntity(City city) {
        return CityDto.builder()
                .id(city.getId())
                .name(city.getName())
                .countries(city.getCountries() != null ?
                        city.getCountries().stream()
                                .map(CountryDto::fromEntity)
                                .collect(Collectors.toList()) : null)
                .build();
    }

    public City toEntity() {
        return new City(
                this.id,
                this.name
        );
    }
}
