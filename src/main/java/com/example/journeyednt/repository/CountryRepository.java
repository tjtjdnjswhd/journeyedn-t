package com.example.journeyednt.repository;

import com.example.journeyednt.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Integer> {
    Optional<Country> findByName(String name);

    @Query("SELECT c FROM Country c WHERE c.city.id = :cityId")
    List<Country> findCountriesByCityId(@Param("cityId") Integer cityId);
    // 해당 cityId에 따른 시/군/구를 가져오는 쿼리

    @Query("SELECT c.name FROM Country c WHERE c.city.name = :cityName")
    List<Country> findCountriesNameByCityName(@Param("cityName") String cityName);
    // 해당 시/도 이름으로 해당하는 시/군/구의 이름을 가져오는 쿼리

    @Query("SELECT c.city.id, c.id FROM Country c WHERE c.name = :name")
    Optional<Object[]> findCityIdAndCountryIdByName(@Param("name") String name);
    // Country 이름으로 City와 Country의 id를 찾는 쿼리

    @Query("SELECT c.id FROM Country c WHERE c.name = :name")
    Integer findIdByName(@Param("name") String name);
    // name으로 id를 찾을 수 있는 쿼리

    @Query("SELECT c.city.name, c.name, c.id FROM Country c")
    List<Object[]> findAllCityCountry();
}
