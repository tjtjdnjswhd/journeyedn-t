package com.example.journeyednt.repository;

import com.example.journeyednt.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Integer> {
    Optional<City> findByName(String name);
    // 시/도 이름으로 찾기

    @Query("SELECT c.name FROM City c")
    List<City> findAll();
    // 모든 city 가져오기

    @Query("SELECT c.id FROM City c WHERE c.name = :name")
    Integer findIdByName(@Param("name") String name);
    // 시/도 이름으로 시/도 id를 가져오는 쿼리
}
