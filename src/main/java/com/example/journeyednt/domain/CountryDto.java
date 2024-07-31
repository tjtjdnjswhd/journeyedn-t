package com.example.journeyednt.domain;

import com.example.journeyednt.entity.City;
import com.example.journeyednt.entity.Country;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CountryDto {
    private Integer id;
    private String name;
    private City city;

    public static CountryDto fromEntity(Country country) {
        return CountryDto.builder()
                .id(country.getId())
                .name(country.getName())
                .city(country.getCity())
                .build();
    }

    public Country toEntity() {
        return new Country(
                this.id,
                this.name,
                this.city
        );
    }

}
