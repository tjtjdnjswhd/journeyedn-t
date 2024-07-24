package com.example.journeyednt.repository;


import com.example.journeyednt.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    Optional<Post> findById(Integer id); // id로 게시글 찾기

    List<Post> findByIsVisible(Boolean isVisible); // 공개/비공개 게시글 검색

    List<Post> findByIsNotice(Boolean isNotice); // 공지사항/일반 게시글 검색

    List<Post> findByTitle(String title); // 제목으로 게시글을 찾기

    List<Post> findByContent(String content); // 내용으로 게시글을 찾기

    List<Post> findByTitleAndContent(String title, String content); // 제목, 내용으로 게시글을 찾기

    @Modifying
    @Transactional
    @Query("UPDATE Post p SET p.isVisible = :isVisible")
    int updateAllPostsVisibility(Boolean isVisible);
    // 모든 게시글의 공개 상태를 변경하는 벌크 업데이트

    @Modifying
    @Transactional
    @Query("UPDATE Post p SET p.isVisible = :isVisible WHERE p.user.id = :userId")
    int updateUserPostsVisibility(Integer userId, Boolean isVisible);
    // 특정 사용자의 모든 게시글의 공개 상태를 변경하는 벌크 업데이트

}


