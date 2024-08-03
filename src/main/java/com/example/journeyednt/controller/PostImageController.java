package com.example.journeyednt.controller;

import com.example.journeyednt.entity.PostImage;
import com.example.journeyednt.service.PostImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/post-images")
@RequiredArgsConstructor
public class PostImageController {

    private final PostImageService postImageService;

    // READ: postId에 해당하는 PostImage 목록을 반환
    @GetMapping("/post/{postId}")
    public String getPostImagesByPostId(@PathVariable Integer postId, Model model) {
        List<PostImage> postImages = postImageService.getPostImagesByPostId(postId);
        model.addAttribute("postImages", postImages);
        return "postImage/list";
    }

    // READ: 특정 contentType에 해당하는 PostImage 목록을 반환
    @GetMapping("/content-type/{contentType}")
    public String getPostImagesByContentType(@PathVariable String contentType, Model model) {
        List<PostImage> postImages = postImageService.getPostImagesByContentType(contentType);
        model.addAttribute("postImages", postImages);
        return "postImage/listByContentType";
    }

    // READ: postId에 해당하는 메인 PostImage를 반환
    @GetMapping("/post/{postId}/primary")
    public String getPrimaryPostImageByPostId(@PathVariable Integer postId, Model model) {
        Optional<PostImage> postImage = postImageService.getPrimaryPostImageByPostId(postId);
        model.addAttribute("postImage", postImage.orElse(null)); // null  Optional<PostImage>가 비어 있을 경우에 대한 처리
        return "postImage/primary";
    }

    // READ: postId와 contentType에 해당하는 PostImage 목록을 반환
    @GetMapping("/post/{postId}/content-type/{contentType}")
    public String getPostImagesByPostIdAndContentType(@PathVariable Integer postId, @PathVariable String contentType, Model model) {
        List<PostImage> postImages = postImageService.getPostImagesByPostIdAndContentType(postId, contentType);
        model.addAttribute("postImages", postImages);
        return "postImage/listByPostIdAndContentType";
    }

    // GET: id에 해당하는 PostImage 편집 폼을 반환
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Integer id, Model model) {
        PostImage postImage = postImageService.findById(id);
        model.addAttribute("postImage", postImage);
        return "postImage/edit";
    }

    // POST: id에 해당하는 PostImage 편집 폼에서 데이터 제출
    @PostMapping("/{id}/edit")
    public String edit(@PathVariable("id") Integer id, @ModelAttribute PostImage updatedPostImage) {
        postImageService.updatePostImage(id, updatedPostImage.getContentType(), updatedPostImage.getData(), updatedPostImage.getIsPrimary());
        return "redirect:/post-images/post/" + id;
    }

    // DELETE: id에 해당하는 PostImage를 삭제
    @GetMapping("/{id}/delete")
    public String deletePostImage(@PathVariable("id") Integer id) {
        postImageService.deletePostImageById(id);
        return "redirect:/post-images";
    }
}