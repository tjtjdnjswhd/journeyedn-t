package com.example.journeyednt.repository;

import com.example.journeyednt.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    boolean existsByIdAndUserId(Integer id, Integer userId);

    @Modifying
    @Query(value = "UPDATE Post p SET p.isVisible = :isVisible WHERE p.id = :id")
    int updatePostVisibility(@Param("id") Integer id, @Param("isVisible") Boolean isVisible);

    // 특정 사용자의 모든 게시글의 공개 상태를 변경하는 벌크 업데이트
    @Modifying
    @Query(value = "UPDATE Post p SET p.isVisible = :isVisible WHERE p.user.id = :userId")
    int updateUserPostsVisibility(Integer userId, Boolean isVisible);

    @Modifying
    @Query(value = "UPDATE Post p SET p.cdd_id = :cdd_id WHERE p.id = :id", nativeQuery = true)
    int updateCddId(@Param("id") Integer id, @Param("cdd_id") Integer cddId);

    @Modifying
    @Query(value = "UPDATE Post p SET p.title = :title, p.content = :content WHERE p.id = :id AND p.isNotice")
    int updateNotice(@Param("id") Integer id, @Param("title") String title, @Param("content") String content);

    List<Post> findByIsNoticeTrue(Pageable pageable);

    @Query(value = "SELECT p.* FROM Post p JOIN country c WHERE (p.title LIKE CONCAT('%', :text, '%') OR :text MEMBER OF (p.tags)) AND p.is_visible = true ORDER BY p.created_at", nativeQuery = true)
    List<Post> findByIsVisibleTrueAndByTextOrderByCreateAtDesc(String text, Pageable pageable);

    @Query(value = "SELECT p.* FROM Post p JOIN country c WHERE (p.title LIKE CONCAT('%', :text, '%') OR :text MEMBER OF (p.tags)) AND p.is_visible = true ORDER BY p.rating", nativeQuery = true)
    List<Post> findByIsVisibleTrueAndByTextOrderByRatingDesc(String text, Pageable pageable);

    List<Post> findByCountryIdAndIsVisibleTrueOrderByCreatedAtDesc(Integer countryId, Pageable pageable);

    List<Post> findByCountryIdAndIsVisibleTrueOrderByRatingDesc(Integer countryId, Pageable pageable);

    @Query(value = "SELECT p.* FROM Post p JOIN country c WHERE c.id = :countryId AND (p.title LIKE CONCAT('%', :text, '%') OR :text MEMBER OF (p.tags)) AND p.is_visible = true ORDER BY p.created_at", nativeQuery = true)
    List<Post> findByCountryIdAndIsVisibleTrueAndByTextOrderByCreateAtDesc(Integer countryId, String text, Pageable pageable);

    @Query(value = "SELECT p.* FROM Post p JOIN country c WHERE c.id = :countryId AND (p.title LIKE CONCAT('%', :text, '%') OR :text MEMBER OF (p.tags)) AND p.is_visible = true ORDER BY p.rating", nativeQuery = true)
    List<Post> findByCountryIdAndIsVisibleTrueAndByTextOrderByRatingDesc(Integer countryId, String text, Pageable pageable);

    List<Post> findByIsVisibleTrueOrderByCreatedAtDesc(Pageable pageable);

    List<Post> findByIsVisibleTrueOrderByRatingDesc(Pageable pageable);

}
