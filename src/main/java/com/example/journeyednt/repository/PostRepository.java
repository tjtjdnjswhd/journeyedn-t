package com.example.journeyednt.repository;

import com.example.journeyednt.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    // 공개/비공개 게시글 검색
    List<Post> findByIsVisible(Boolean isVisible);

    // 공지사항/일반 게시글 검색
    List<Post> findByIsNotice(Boolean isNotice);

    // 내용으로 게시글을 찾기
    List<Post> findByContent(String content);

    // 제목, 내용으로 게시글을 찾기
    List<Post> findByTitleAndContent(String title, String content);

    // 모든 게시글의 공개 상태를 변경하는 벌크 업데이트
    @Modifying
    @Query(value = "UPDATE Post p SET p.isVisible = :isVisible")
    int updateAllPostsVisibility(Boolean isVisible);

    // 특정 사용자의 모든 게시글의 공개 상태를 변경하는 벌크 업데이트
    @Modifying
    @Query(value = "UPDATE Post p SET p.isVisible = :isVisible WHERE p.user.id = :userId")
    int updateUserPostsVisibility(Integer userId, Boolean isVisible);

    // 시/도, 시/군/구로 공개 게시물 최신순으로 조회
    @Query(value = "SELECT p FROM Post p JOIN p.country c JOIN c.city ci WHERE ci.id = :cityId AND c.id = :countryId AND p.isVisible = true ORDER BY p.createAt DESC")
    List<Post> findByCityAndCountryOrderByCreateAt(@Param("cityId") Integer cityId, @Param("countryId") Integer countryId);

    // 시/도, 시/군/구로 공개 게시물 평점순으로 조회
    @Query(value = "SELECT p FROM Post p JOIN p.country c JOIN c.city ci WHERE ci.id = :cityId AND c.id = :countryId AND p.isVisible = true ORDER BY p.rating DESC")
    List<Post> findByCityAndCountryOrderByRating(@Param("cityId") Integer cityId, @Param("countryId") Integer countryId);

    // 시/도, 시/군/구, 그리고 태그를 포함하는 공개게시물 최신순으로 조회 -> String
    @Query(value = "SELECT p FROM Post p JOIN p.country c JOIN c.city ci WHERE ci.id = :cityId AND c.id = :countryId AND p.isVisible = true AND :tag MEMBER OF (p.tags) ORDER BY p.createAt DESC", nativeQuery = true)
    List<Post> findByCityCountryAndTagOrderByCreateAt(@Param("cityId") Integer cityId, @Param("countryId") Integer countryId, @Param("tag") String tag);

    // 시/도, 시/군/구, 그리고 태그를 포함하는 공개게시물 평점순으로 조회 -> String
    @Query(value = "SELECT p FROM Post p JOIN p.country c JOIN c.city ci WHERE ci.id = :cityId AND c.id = :countryId AND p.isVisible = true AND :tag MEMBER OF (p.tags) ORDER BY p.rating DESC", nativeQuery = true)
    List<Post> findByCityCountryAndTagOrderByRating(@Param("cityId") Integer cityId, @Param("countryId") Integer countryId, @Param("tag") String tag);

    // 시/도, 시/군/구, 그리고 제목을 포함하는 공개게시물 최신순으로 조회
    @Query(value = "SELECT p FROM Post p JOIN p.country c JOIN c.city ci WHERE ci.id = :cityId AND c.id = :countryId AND p.isVisible = true AND p.title LIKE %:title% ORDER BY p.createAt DESC")
    List<Post> findByCityCountryAndTitleOrderByCreateAt(@Param("cityId") Integer cityId, @Param("countryId") Integer countryId, @Param("title") String title);

    // 시/도, 시/군/구, 그리고 제목을 포함하는 공개게시물 평점순으로 조회
    @Query(value = "SELECT p FROM Post p JOIN p.country c JOIN c.city ci WHERE ci.id = :cityId AND c.id = :countryId AND p.isVisible = true AND p.title LIKE %:title% ORDER BY p.rating DESC")
    List<Post> findByCityCountryAndTitleOrderByRating(@Param("cityId") Integer cityId, @Param("countryId") Integer countryId, @Param("title") String title);

    // 모든 공개 게시물을 최신순으로 조회
    @Query(value = "SELECT p FROM Post p WHERE p.isVisible = true ORDER BY p.createAt DESC")
    List<Post> findAllPostsByOrderByCreatedAtDesc();

    // 모든 공개 게시물을 평점순으로 조회
    @Query(value = "SELECT p FROM Post p WHERE p.isVisible = true ORDER BY p.rating DESC")
    List<Post> findAllPostsByOrderByRatings();

    // 제목으로 공개 게시물 찾고 최신순으로 조회
    List<Post> findByTitleAndIsVisibleTrueOrderByCreateAtDesc(String title);

    // 제목으로 공개 게시물 찾고 평점순으로 조회
    List<Post> findByTitleAndIsVisibleTrueOrderByRatingDesc(String title);

    // 특정 태그로 공개 게시물 찾고 최신순으로 조회 -> String
    @Query(value = "SELECT p FROM Post p WHERE :tag MEMBER OF (p.tags) AND p.isVisible = true ORDER BY p.createAt DESC", nativeQuery = true)
    List<Post> findByTagOrderByCreateAtDesc(@Param("tag") String tag);

    // 특정 태그로 공개 게시물 찾고 평점순으로 조회 -> String
    @Query(value = "SELECT p FROM Post p WHERE :tag MEMBER OF (p.tags) AND p.isVisible = true ORDER BY p.rating DESC", nativeQuery = true)
    List<Post> findByTagOrderByRatingDesc(@Param("tag") String tag);
}
