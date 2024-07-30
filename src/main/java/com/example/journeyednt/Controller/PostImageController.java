package com.example.journeyednt.Controller;
import com.example.journeyednt.entity.PostImage;
import com.example.journeyednt.service.PostImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/post-images")
public class PostImageController {private final PostImageService postImageService;

    @Autowired
    public PostImageController(PostImageService postImageService) {
        this.postImageService = postImageService;
    }

    // postId에 해당하는 PostImage 목록을 반환
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<PostImage>> getPostImagesByPostId(@PathVariable Integer postId) {
        List<PostImage> postImages = postImageService.getPostImagesByPostId(postId);
        return ResponseEntity.ok(postImages);
    }

    //특정 contentType에 해당하는 PostImage 목록을 반환
    @GetMapping("/content-type/{contentType}")
    public ResponseEntity<List<PostImage>> getPostImagesByContentType(@PathVariable String contentType) {
        List<PostImage> postImages = postImageService.getPostImagesByContentType(contentType);
        return ResponseEntity.ok(postImages);
    }

    //postId에 해당하는 주요 PostImage를 반환
    @GetMapping("/post/{postId}/primary")
    public ResponseEntity<Optional<PostImage>> getPrimaryPostImageByPostId(@PathVariable Integer postId) {
        Optional<PostImage> postImage = postImageService.getPrimaryPostImageByPostId(postId);
        return ResponseEntity.ok(postImage);
    }

    //postId와 contentType에 해당하는 PostImage 목록을 반환
    @GetMapping("/post/{postId}/content-type/{contentType}")
    public ResponseEntity<List<PostImage>> getPostImagesByPostIdAndContentType(@PathVariable Integer postId, @PathVariable String contentType) {
        List<PostImage> postImages = postImageService.getPostImagesByPostIdAndContentType(postId, contentType);
        return ResponseEntity.ok(postImages);
    }

    //새로운 PostImage를 생성
    @PostMapping
    public ResponseEntity<PostImage> createPostImage(@RequestBody PostImage postImage) {
        PostImage createdPostImage = postImageService.savePostImage(postImage);
        return ResponseEntity.ok(createdPostImage);
    }

    // id에 해당하는 PostImage를 업데이트
    @PutMapping("/{id}")
    public ResponseEntity<PostImage> updatePostImage(@PathVariable Integer id, @RequestBody PostImage postImage) {
        PostImage updatedPostImage = postImageService.updatePostImage(id, postImage.getContentType(), postImage.getData(), postImage.getIsPrimary());
        return ResponseEntity.ok(updatedPostImage);
    }

    //id에 해당하는 PostImage를 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePostImage(@PathVariable Integer id) {
        postImageService.deletePostImageById(id);
        return ResponseEntity.noContent().build();
    }
}