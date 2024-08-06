package com.example.journeyednt.controller;

import com.example.journeyednt.domain.post.PostCreate;
import com.example.journeyednt.domain.post.PostDto;
import com.example.journeyednt.domain.user.UserDto;
import com.example.journeyednt.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
    private final PostImageService postImageService;
    private final UserService userService;
    private final CountryService countryService;

    @GetMapping
    public String search(@RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(required = false) String text,
                         @RequestParam(required = false) Integer countryId,
                         @RequestParam(required = false) String orderBy,
                         Model model,
                         HttpServletRequest request) {
        List<PostDto> posts;
        if (countryId == null) {
            posts = postService.getPosts(text, page, orderBy);
        } else if (text == null) {
            posts = postService.getPosts(countryId, page, orderBy);
        } else {
            posts = postService.getPosts(countryId, text, page, orderBy);
        }

        if (countryId != null) {
            String address = countryService.getFullNameByPostId(countryId);
            model.addAttribute("address", address);
        }

        model.addAttribute("page", page);
        model.addAttribute("url", request.getRequestURI());
        model.addAttribute("posts", posts);
        return "postSearch";
    }

    // 게시글 상세보기(상세페이지)
    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Integer id, Model model, Principal principal) {
        PostDto postDto = postService.getPostById(id);
        UserDto user = userService.findByPostId(postDto.getId());

        List<Integer> imageIds = postImageService.getPostImageIdsByPostId(id);
        String addressName = countryService.getFullNameByPostId(id);

        model.addAttribute("post", postDto);
        model.addAttribute("writerNickName", user.getNickName());
        model.addAttribute("writerAccountId", user.getAccountId());
        model.addAttribute("imageIds", imageIds);
        model.addAttribute("address", addressName);

        UserDto currentUser = userService.findByAccountId(principal.getName());
        model.addAttribute("currentUser", currentUser);

        return "postDetail";
    }

    // 게시글 작성 폼
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("post", new PostCreate()); // 빈 객체 추가
        return "postCreate";
    }

    // 게시글 저장(생성)
    @PostMapping("/create")
    public String create(@ModelAttribute @Valid PostCreate postCreate, Principal principal) { // Principal 현재 인증된 사용자의 정보를 가져온다
        String accountId = principal.getName(); // accountId

        UserDto user = userService.findByAccountId(accountId);

        PostDto savedPost = postService.createPost(postCreate, user.getId());
        return "redirect:/posts/" + savedPost.getId();
    }

    // 게시글 수정 폼
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Integer id, Model model) {
        PostDto postDto = postService.getPostById(id);
        PostCreate postCreate = new PostCreate();
        postCreate.setTitle(postDto.getTitle());
        postCreate.setContent(postDto.getContent());
        postCreate.setRating(postDto.getRating());
        postCreate.setTags(postDto.getTags());
        postCreate.setCountryId(postDto.getCountry().getId());
        model.addAttribute("post", postCreate);
        return "postCreate";
    }

    // 게시글 수정
    @PostMapping("/{id}/edit")
    // PRG 패턴을 사용, 폼 제출 후 리다이렉트를 통해 페이지를 새로고침 했을 때 폼이 중복 제출되는 것을 방지
    public String edit(@PathVariable("id") Integer id, @ModelAttribute @Valid PostCreate postCreate, RedirectAttributes redirectAttributes, Principal principal) {
        String accountId = principal.getName();
        UserDto loginUser = userService.findByAccountId(accountId);

        if (!postService.existsByIdAndUserId(id, loginUser.getId())) {
            return "redirect:/posts/" + id;
        }

        try {
            PostDto updatedPost = postService.updatePost(id, postCreate);
            redirectAttributes.addFlashAttribute("message", "게시글이 성공적으로 수정되었습니다.");
            return "redirect:/posts/" + updatedPost.getId(); // 수정 성공 시 성공메세지와 함께 게시글 상세페이지로 리다이렉트
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/posts/" + id + "edit"; // 수정 실패 시 에러메세지와 함께 게시글 수정 폼으로 리다이렉트
        }
    }

    @GetMapping("/{id}/delete")
    public String deletePost(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("id", id);
        return "deletePost";
    }

    // 게시글 삭제
    @PostMapping("/{id}/delete")
    public String deletePost(@PathVariable("id") Integer id, Principal principal) {
        String accountId = principal.getName();
        UserDto loginUser = userService.findByAccountId(accountId);

        if (!postService.existsByIdAndUserId(id, loginUser.getId())) {
            return "redirect:/posts/" + id;
        }

        postService.invisiblePost(id);
        return "redirect:/posts";
    }
}
