package com.example.journeyednt.controller;

import com.example.journeyednt.entity.PostImage;
import com.example.journeyednt.service.PostImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
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
    public ResponseEntity<Resource> getPostImagesByPostId(@PathVariable Integer id) {
        PostImage postImage = postImageService.findById(id);
        ByteArrayResource resource = new ByteArrayResource(postImage.getData());
        MediaType mediaType = MediaType.parseMediaType(postImage.getContentType());

        return ResponseEntity.ok().contentType(mediaType).body(resource);
    }
}
