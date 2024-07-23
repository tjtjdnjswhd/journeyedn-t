package com.example.journeyednt.repository;

import com.example.journeyednt.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Integer> {
//    Optional<PostImage> findById(int id); // id로 이미지 찾기

    List<PostImage> findByPostId(int postId); // 특정 게시글의 모든 이미지 찾기

    List<PostImage> findByContentType(String contentType); // 특정 콘텐츠 타입의 이미지 찾기

    Optional<PostImage> findByPostIdAndIsPrimary(Long postId, boolean isPrimary); // 특정 게시글의 주 이미지 찾기

    List<PostImage> findByPostIdAndContentType(Long postId, String contentType); // 특정 게시글의 특정 콘텐츠 타입 이미지 찾기

//    List<PostImage> findByPost(Post post);

}