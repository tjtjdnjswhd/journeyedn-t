package com.example.journeyednt.service;

import com.example.journeyednt.entity.PostImage;
import com.example.journeyednt.repository.PostImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PostImageService {

    private final PostImageRepository postImageRepository;

    @Autowired
    public PostImageService(PostImageRepository postImageRepository) {
        this.postImageRepository = postImageRepository;
    }

    // 특정 게시물의 모든 이미지를 조회
    @Transactional(readOnly = true)
    public List<PostImage> getPostImagesByPostId(Integer postId) {
        return postImageRepository.findByPostId(postId);
    }

    // 특정 콘텐츠 타입의 이미지를 조회
    @Transactional(readOnly = true)
    public List<PostImage> getPostImagesByContentType(String contentType) {
        return postImageRepository.findByContentType(contentType);
    }

    // 특정 게시물의 주 이미지를 조회
    @Transactional(readOnly = true)
    public Optional<PostImage> getPrimaryPostImageByPostId(Integer postId) {
        return postImageRepository.findByPostIdAndIsPrimary(postId, true);
    }

    // 특정 게시물의 특정 콘텐츠 타입의 이미지를 조회
    @Transactional(readOnly = true)
    public List<PostImage> getPostImagesByPostIdAndContentType(Integer postId, String contentType) {
        return postImageRepository.findByPostIdAndContentType(postId, contentType);
    }

    // 새로운 이미지를 저장
    @Transactional
    public PostImage savePostImage(PostImage postImage) {
        return postImageRepository.save(postImage);
    }

    // 특정 ID의 이미지를 삭제
    @Transactional
    public void deletePostImageById(Integer id) {
        postImageRepository.deleteById(id);
    }

    // 특정 이미지를 업데이트
    @Transactional
    public PostImage updatePostImage(Integer id, String contentType, byte[] data, boolean isPrimary) {
        Optional<PostImage> optionalPostImage = postImageRepository.findById(id);
        if (optionalPostImage.isPresent()) {
            PostImage postImage = optionalPostImage.get();
            postImage.updatePostImage(contentType, data, isPrimary);
            return postImageRepository.save(postImage);
        } else {
            throw new IllegalArgumentException("PostImage not found with id: " + id);
        }
    }
}