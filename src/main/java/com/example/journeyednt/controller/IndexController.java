package com.example.journeyednt.controller;

import com.example.journeyednt.domain.post.PostDto;
import com.example.journeyednt.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final PostService postService;

    public String index(@RequestParam(defaultValue = "1", required = false) Integer page, Model model) {
        List<PostDto> posts = postService.getPosts(page, PostService.ORDER_BY_RECENT);
        List<PostDto> notices = postService.getNotices(5);
        model.addAttribute("notices",notices);
        model.addAttribute("posts", posts);
        return "index";
    }
}
