package com.example.journeyednt.controller;

import com.example.journeyednt.domain.post.PostCreate;
import com.example.journeyednt.domain.post.PostDto;
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

    @GetMapping
    public String search(@RequestParam(defaultValue = "1") Integer page,
                         @RequestParam String text,
                         @RequestParam(required = false) Integer countryId,
                         @RequestParam(required = false) String orderBy,
                         Model model) {
        List<PostDto> posts;
        if (countryId == null) {
            posts = postService.getPosts(text, page, orderBy);
        } else if (text == null) {
            posts = postService.getPosts(countryId, page, orderBy);
        } else {
            posts = postService.getPosts(countryId, text, page, orderBy);
        }

        model.addAttribute("posts", posts);
        return "postSearch";
    }

    // 게시글 상세보기(상세페이지)
    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {
        PostDto postDto = postService.getPostById(id);
        User user = userService.findByPostId(postDto.getId());
        model.addAttribute("post", postDto);
        model.addAttribute("nickName", user.getNickName());
        return "postDetail";
    }

    // 게시글 작성 폼
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("post", PostDto.createEmpty()); // 빈 객체 추가
        return "postCreate";
    }

    // 게시글 저장(생성)
    @PostMapping("/create")
    public String create(@ModelAttribute PostCreate postCreate, Principal principal) { // Principal 현재 인증된 사용자의 정보를 가져온다
        String nickname = principal.getName(); // 닉네임을 가져옴

        User user = userService.findByNickname(nickname); // 닉네임으로 User 객체 조회

        PostDto savedPost = postService.createPost(postCreate, user.getId());
        return "redirect:/posts/" + savedPost.getId();
    }

    // 게시글 수정 폼
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Integer id, Model model) {
        PostDto postDto = postService.getPostById(id);
        model.addAttribute("post", postDto);
        return "postEdit";
    }

    // 게시글 수정
    @PostMapping("/{id}/edit")
    // PRG 패턴을 사용, 폼 제출 후 리다이렉트를 통해 페이지를 새로고침 했을 때 폼이 중복 제출되는 것을 방지
    public String edit(@PathVariable("id") Integer id, @ModelAttribute PostCreate postCreate, RedirectAttributes redirectAttributes) {
        try {
            PostDto updatedPost = postService.updatePost(id, postCreate);
            redirectAttributes.addFlashAttribute("message", "게시글이 성공적으로 수정되었습니다.");
            return "redirect:/posts/" + updatedPost.getId(); // 수정 성공 시 성공메세지와 함께 게시글 상세페이지로 리다이렉트
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/posts/" + id + "edit"; // 수정 실패 시 에러메세지와 함께 게시글 수정 폼으로 리다이렉트
        }
    }

    // 게시글 삭제
    @PostMapping("/{id}/delete")
    public String deletePost(@PathVariable("id") Integer id) {
        postService.invisiblePost(id);
        return "redirect:/posts";
    }
}
