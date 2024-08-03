package com.example.journeyednt.service;

import com.example.journeyednt.entity.PostImage;
import com.example.journeyednt.repository.PostImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostImageService {

    private final PostImageRepository postImageRepository;

    @Transactional(readOnly = true)
    public List<Integer> getPostImageIdsByPostId(Integer postId) {
        return postImageRepository.findIdsByPostId(postId);
    }

    @Transactional(readOnly = true)
    public Integer getPostPrimaryImageByPostId(Integer postId) {
        return postImageRepository.findPrimaryImageIdByPostId(postId);
    }

    // 새로운 이미지를 저장
    @Transactional
    public PostImage savePostImage(PostImage postImage) {
        return postImageRepository.save(postImage);
    }

    @Transactional(readOnly = true)
    public PostImage findById(Integer id) {
        return postImageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("PostImage not found with id: " + id));
    }
}
