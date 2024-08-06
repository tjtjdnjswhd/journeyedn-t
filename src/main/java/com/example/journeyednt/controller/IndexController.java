package com.example.journeyednt.controller;

import com.example.journeyednt.domain.post.PostDto;
import com.example.journeyednt.repository.CountryRepository;
import com.example.journeyednt.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final PostService postService;
    private final CountryRepository countryRepository;

    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "1", required = false) Integer page, Model model, HttpServletRequest request) {
        List<PostDto> posts = postService.getPosts(page, PostService.ORDER_BY_RECENT);
        List<PostDto> notices = postService.getNotices(5);
        List<String> topCityNames = countryRepository.findRatingTopNames(5);
        model.addAttribute("topCity", topCityNames);
        model.addAttribute("notices",notices);
        model.addAttribute("posts", posts);
        model.addAttribute("page", page);
        model.addAttribute("url", request.getContextPath());
        return "index";
    }
}
