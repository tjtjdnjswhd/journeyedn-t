package com.example.journeyednt.controller;

import com.example.journeyednt.domain.user.UserCreateDto;
import com.example.journeyednt.domain.user.UserLoginDto;
import com.example.journeyednt.entity.User;
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

@Controller
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("/users")
@Validated
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signup(Model model) {
        UserCreateDto userCreateDto = UserCreateDto.createEmpty();
        model.addAttribute("userCreateDto", userCreateDto);
        return "signup";
    }

    @PostMapping
    public String signup(@ModelAttribute @Valid UserCreateDto userCreateDto, Model model) {
        User user = userService.createUser(userCreateDto);
        model.addAttribute("user", user);
        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        UserLoginDto userLoginDto = UserLoginDto.createEmpty();
        model.addAttribute("userLoginDto", userLoginDto);
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute @Valid UserLoginDto userLoginDto, Model model) {
        if (!userService.findUserIsVisibleByAccountId(userLoginDto.getAccountId())) {
            throw new UserException(ErrorCode.USER_INACTIVE);
        }

        if (userLoginDto.getIsVisible() == null) {
            throw new UserException(ErrorCode.USER_TYPE_NOT_NONE);
        }

        if (userService.findUserRoleByAccountId(userLoginDto.getAccountId())
                .map(role -> role.getName().equals("정지"))
                .orElse(false)) {
            throw new UserException(ErrorCode.ACCESS_DENIED);
        }

        User user = userService.loginUser(userLoginDto);
        model.addAttribute("user", user);
        return "redirect:/";
    }

    @PostMapping("/{id}/withdraw")
    public String withdraw(@PathVariable("id") Integer userId) {
        userService.updateVisibleUser(userId, false);
        userService.updateUserRole(userId, "정지");
        return "redirect:/";
    }
}
