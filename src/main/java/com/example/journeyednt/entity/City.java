package com.example.journeyednt.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "city")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT")
    private Integer id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private List<Country> countries = new ArrayList<>();

    @Builder
    public City(Integer id, String name) {
        this.name = name;
    }

    public void updateCity(String name) {
        this.name = name;
    }

    public static City of(String name) {
        return City.builder()
                .name(name)
                .build();
    }
}
