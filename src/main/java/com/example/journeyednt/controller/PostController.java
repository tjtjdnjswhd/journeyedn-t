package com.example.journeyednt.controller;

import com.example.journeyednt.domain.PostDto;
import com.example.journeyednt.entity.User;
import com.example.journeyednt.service.PostService;
import com.example.journeyednt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;

    // 모든 게시글 조회 (메인페이지)
    @GetMapping
    public String getAllPosts(@RequestParam String orderBy, Model model) {
        List<PostDto> posts = postService.getAllPosts(orderBy);
        model.addAttribute("posts", posts);
        return "postList";
    }

    // 제목으로 게시글 조회
    @GetMapping("/title")
    public String SearchTitle(@RequestParam String title, @RequestParam String orderby, Model model) {
        List<PostDto> posts = postService.findVisiblePostsByTitle(title, orderby);
        model.addAttribute("posts", posts);
        return "postList";
    }

    // 특정 태그로 게시글 조회
    @GetMapping("/tag")
    public String SearchTag(@RequestParam String tag, @RequestParam String orderby, Model model) {
        List<PostDto> posts = postService.getPostsByTag(tag, orderby);
        model.addAttribute("posts", posts);
        return "postList";
    }

    // 시/도, 시/군/구로 게시물 조회
    @GetMapping("/location")
    public String SearchLocation(@RequestParam String cityName, @RequestParam String countryName, @RequestParam String orderby, Model model) {
        List<PostDto> posts = postService.getPostsByCityAndCountry(cityName, countryName, orderby);
        model.addAttribute("posts", posts);
        return "postList";
    }

    // 시/도, 시/군/구, 태그로 게시물 조회
    @GetMapping("/location-tag")
    public String SearchLocationTag(@RequestParam String cityName, @RequestParam String countryName, @RequestParam String tag, @RequestParam String orderby, Model model) {
        List<PostDto> posts = postService.getPostsByCityCountryAndTag(cityName, countryName, tag, orderby);
        model.addAttribute("posts", posts);
        return "postList";
    }

    // 시/도, 시/군/구, 제목으로 게시물 조회
    @GetMapping("/location-title")
    public String SearchLocationTitle(@RequestParam String cityName, @RequestParam String countryName, @RequestParam String title, @RequestParam String orderby, Model model) {
        List<PostDto> posts = postService.getPostsByCityCountryAndTitle(cityName, countryName, title, orderby);
        model.addAttribute("posts", posts);
        return "postList";
    }

    // 게시글 상세보기(상세페이지)
    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {
        PostDto postDto = postService.getPostById(id);
        model.addAttribute("post", postDto);
        return "postDetail";
    }

    // 게시글 작성 폼
    @GetMapping("/new")
    public String newPostForm(Model model) {
        model.addAttribute("post", PostDto.createEmpty()); // 빈 객체 추가
        return "postForm";
    }

    // 게시글 저장(생성)
    @PostMapping("/new")
    public String savePost(@ModelAttribute PostDto postDto, Principal principal) { // Principal 현재 인증된 사용자의 정보를 가져온다
        String nickname = principal.getName(); // 닉네임을 가져옴

        User user = userService.findByNickname(nickname); // 닉네임으로 User 객체 조회

        PostDto savedPost = postService.createPost(postDto, user);
        return "redirect:/posts/" + savedPost.getId(); // 새로 생성된 게시글의 상세 페이지로 리다이렉트
        // html 파일 th:text="${postDto.user.nickname} 작성해야 닉네임 표시 가능
    }

    // 게시글 수정 폼
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Integer id, Model model) {
        PostDto postDto = postService.getPostById(id);
        model.addAttribute("postedit", postDto);
        return "editPostform";
    }

    // 게시글 수정
    @PostMapping("/{id}/edit")
    // PRG 패턴을 사용, 폼 제출 후 리다이렉트를 통해 페이지를 새로고침 했을 때 폼이 중복 제출되는 것을 방지
    public String edit(@PathVariable("id") Integer id, @ModelAttribute PostDto postDto, RedirectAttributes redirectAttributes) {
        try {
            PostDto updatedPost = postService.updatePost(id, postDto);
            redirectAttributes.addFlashAttribute("message", "게시글이 성공적으로 수정되었습니다.");
            return "redirect:/posts/" + updatedPost.getId(); // 수정 성공 시 성공메세지와 함께 게시글 상세페이지로 리다이렉트
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/posts/" + id + "/edit"; // 수정 실패 시 에러메세지와 함께 게시글 수정 폼으로 리다이렉트
        }
    }

    // 게시글 삭제
    @PostMapping("/{id}/delete")
    public String deletePost(@PathVariable("id") Integer id) {
        postService.deletePost(id);
        return "redirect:/posts";
    }
}
