package com.example.journeyednt.controller;

import com.example.journeyednt.domain.user.UserDto;
import com.example.journeyednt.domain.user.UserSignup;
import com.example.journeyednt.domain.user.UserLogin;
import com.example.journeyednt.service.PostService;
import com.example.journeyednt.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("/users")
@Validated
public class UserController {

    private final UserService userService;
    private final PostService postService;

    @GetMapping("/signup")
    public String signup(Model model) {
        UserSignup userSignup = UserSignup.createEmpty();
        model.addAttribute("userSignup", userSignup);
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute @Valid UserSignup userCreateDto, Model model) {
        UserDto user = userService.createUser(userCreateDto);
        model.addAttribute("user", user);
        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        UserLogin userLogin = UserLogin.createEmpty();
        model.addAttribute("userLogin", userLogin);
        return "login";
    }

    @PostMapping("/withdraw")
    public String withdraw(Principal principal, HttpServletRequest request) throws ServletException {
        String accountId = principal.getName();

        UserDto user = userService.findByAccountId(accountId);

        userService.updateVisibleUser(accountId, false);
        userService.updateUserRole(accountId, "Ban");
        postService.invisiblePostByUserId(user.getId());
        request.logout();
        return "redirect:/";
    }
}
