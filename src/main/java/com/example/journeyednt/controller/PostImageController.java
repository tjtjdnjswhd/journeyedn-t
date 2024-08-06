package com.example.journeyednt.controller;

import com.example.journeyednt.entity.PostImage;
import com.example.journeyednt.service.PostImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post-images")
@RequiredArgsConstructor
public class PostImageController {

    private final PostImageService postImageService;

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getPostImageById(@PathVariable Integer id) {
        PostImage postImage = postImageService.findById(id);
        ByteArrayResource resource = new ByteArrayResource(postImage.getData());
        MediaType mediaType = MediaType.parseMediaType(postImage.getContentType());

        return ResponseEntity.ok().contentType(mediaType).body(resource);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<Resource> getPostPrimaryImageByPostId(@PathVariable Integer postId) {
        try {
            Integer imageId = postImageService.getPostPrimaryImageByPostId(postId);
            PostImage postImage = postImageService.findById(imageId);
            ByteArrayResource resource = new ByteArrayResource(postImage.getData());
            MediaType mediaType = MediaType.parseMediaType(postImage.getContentType());

            return ResponseEntity.ok().contentType(mediaType).body(resource);
        } catch (Exception e) {
            ClassPathResource classPathResource = new ClassPathResource("static/img/mainImg.png");
            MediaType mediaType = MediaType.IMAGE_PNG;

            return ResponseEntity.ok().contentType(mediaType).body(classPathResource);
        }
    }
}
