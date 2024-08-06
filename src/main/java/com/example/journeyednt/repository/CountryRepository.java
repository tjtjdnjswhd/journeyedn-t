package com.example.journeyednt.repository;

import com.example.journeyednt.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

    Optional<Country> findByName(String name);

    // 해당 cityId에 따른 시/군/구를 가져오는 쿼리
    @Query("SELECT c FROM Country c WHERE c.city.id = :cityId")
    List<Country> findCountriesByCityId(@Param("cityId") Integer cityId);

    // 해당 시/도 이름으로 해당하는 시/군/구의 이름을 가져오는 쿼리
    @Query("SELECT c.name FROM Country c WHERE c.city.name = :cityName")
    List<Country> findCountriesNameByCityName(@Param("cityName") String cityName);

    // Country 이름으로 City와 Country의 id를 찾는 쿼리
    @Query("SELECT c.city.id, c.id FROM Country c WHERE c.name = :name")
    Optional<Object[]> findCityIdAndCountryIdByName(@Param("name") String name);

    // name으로 id를 찾을 수 있는 쿼리
    @Query("SELECT c.id FROM Country c WHERE c.name = :name")
    Integer findIdByName(@Param("name") String name);

    // City의 이름, Country의 이름, id를 가져오는 쿼리
    @Query("SELECT c.city.name, c.name, c.id FROM Country c")
    List<Object[]> findAllCityCountry();

    @Query(value = """
            SELECT CONCAT(c.name, ' ', ct.name) FROM
                (SELECT ct.id, ct.city_id, ct.name, AVG(p.rating) AS avg_rating
                FROM country ct
                JOIN post p
                ON ct.id = p.cdd_id
                GROUP BY ct.id
                ORDER BY avg_rating) AS ct
            JOIN city c
            ON ct.city_id = c.id
            LIMIT :count
            """, nativeQuery = true)
    List<String> findRatingTopNames(@Param("count") Integer count);

    @Query(value = """
            SELECT CONCAT(c.name, ' ', ct.name)
            FROM country ct
            JOIN city c
            ON ct.city_id = c.id
            JOIN post p
            ON ct.id = p.cdd_id
            WHERE p.id = :postId
            """, nativeQuery = true)
    String findFullNameByPostId(Integer postId);

    @Query(value = """
            SELECT CONCAT(c.name, ' ', ct.name)
            FROM country ct
            JOIN city c
            ON ct.city_id = c.id
            WHERE ct.id = :id
            """, nativeQuery = true)
    String findFullNameById(@Param("id") Integer id);
}
