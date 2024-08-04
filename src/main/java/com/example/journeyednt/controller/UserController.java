package com.example.journeyednt.controller;

import com.example.journeyednt.domain.user.UserDto;
import com.example.journeyednt.domain.user.UserSignup;
import com.example.journeyednt.domain.user.UserLogin;
import com.example.journeyednt.exception.UserException;
import com.example.journeyednt.result.ErrorCode;
import com.example.journeyednt.service.UserService;
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

    @GetMapping("/signup")
    public String signup(Model model) {
        UserSignup userCreateDto = UserSignup.createEmpty();
        model.addAttribute("userCreateDto", userCreateDto);
        return "signup";
    }

    @PostMapping
    public String signup(@ModelAttribute @Valid UserSignup userCreateDto, Model model) {
        UserDto user = userService.createUser(userCreateDto);
        model.addAttribute("user", user);
        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        UserLogin userLoginDto = UserLogin.createEmpty();
        model.addAttribute("userLoginDto", userLoginDto);
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute @Valid UserLogin userLoginDto, Model model) {
        UserDto loginUser = userService.findByAccountId(userLoginDto.getAccountId());

        if (!loginUser.getIsVisible()) {
            throw new UserException(ErrorCode.USER_INACTIVE);
        }

        if (userService.findUserRoleByAccountId(userLoginDto.getAccountId()).getName().equals("Ban")) {
            throw new UserException(ErrorCode.ACCESS_DENIED);
        }

        UserDto user = userService.loginUser(userLoginDto);
        model.addAttribute("user", user);
        return "redirect:/";
    }

    @PostMapping("/withdraw")
    public String withdraw(Principal principal) {
        String accountId = principal.getName();

        userService.updateVisibleUser(accountId, false);
        userService.updateUserRole(accountId, "Ban");
        return "redirect:/";
    }
}
