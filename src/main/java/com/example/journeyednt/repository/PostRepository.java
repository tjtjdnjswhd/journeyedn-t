package com.example.journeyednt.repository;


import com.example.journeyednt.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    Optional<Post> findById(Integer id); // id로 게시글 찾기

    List<Post> findByIsVisible(boolean isVisible); // 공개/비공개 게시글 검색

    List<Post> findByIsNotice(boolean isNotice); // 공지사항/일반 게시글 검색

    List<Post> findByTitle(String title); // 제목으로 게시글을 찾기

    List<Post> findByContent(String content); // 내용으로 게시글을 찾기

    List<Post> findByTitleAndContent(String title, String content); // 제목, 내용으로 게시글을 찾기

}


