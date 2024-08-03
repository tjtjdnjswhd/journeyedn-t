package com.example.journeyednt.repository;

import com.example.journeyednt.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Integer> {

    @Query("select p.id from PostImage p where p.post.id = ?1")
    List<Integer> findIdsByPostId(Integer postId);

    @Query("SELECT p.id FROM PostImage  p WHERE p.post.id = ?1 AND p.isPrimary")
    Integer findPrimaryImageIdByPostId(Integer postId);
}
