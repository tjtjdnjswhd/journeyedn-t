package com.example.journeyednt.repository;

import com.example.journeyednt.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Integer> {

    @Query("select p.id from PostImage p where p.post.id = ?1")
    List<Integer> findIdsByPostId(Integer postId);

    @Query("SELECT p.id FROM PostImage  p WHERE p.post.id = ?1 AND p.isPrimary")
    Integer findPrimaryImageIdByPostId(Integer postId);

    // 특정 게시글의 모든 이미지 찾기
    List<PostImage> findByPostId(Integer postId);

    // 특정 콘텐츠 타입의 이미지 찾기
    List<PostImage> findByContentType(String contentType);

    // 특정 게시글의 주 이미지 찾기
    Optional<PostImage> findByPostIdAndIsPrimary(Integer postId, boolean isPrimary);

    // 특정 게시글의 특정 콘텐츠 타입 이미지 찾기
    List<PostImage> findByPostIdAndContentType(Integer postId, String contentType);
}
