package com.example.journeyednt.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "country")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Builder
    public Country(Integer id, String name, City city) {
        this.id = id;
        this.name = name;
        this.city = city;
    }

    public void updateCountry(String name) {
        this.name = name;
    }

    public static Country of(String name, City city) {
        return Country.builder()
                .name(name)
                .city(city)
                .build();
    }
}

