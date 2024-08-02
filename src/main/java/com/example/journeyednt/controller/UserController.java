package com.example.journeyednt.controller;

import com.example.journeyednt.domain.user.UserCreateDto;
import com.example.journeyednt.domain.user.UserDto;
import com.example.journeyednt.domain.user.UserLoginDto;
import com.example.journeyednt.entity.User;
import com.example.journeyednt.exception.UserException;
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

    //    @Operation(summary = "회원가입", description = "유저를 생성합니다.")
    @GetMapping("/createUser")
    public String createUserForm(Model model) {
        UserCreateDto userCreateDto = UserCreateDto.builder()
                .name("")
                .accountId("")
                .passwordHash("")
                .nickName("")
                .build();
        model.addAttribute("userCreateDto", userCreateDto);
        return "createUserForm";
    }

    @PostMapping
    public String createUser(@ModelAttribute @Valid UserCreateDto userCreateDto, Model model) {
        User user = userService.createUser(userCreateDto);
        model.addAttribute("user", user);
        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        UserLoginDto userLoginDto = UserLoginDto.builder()
            .accountId("")
            .passwordHash("")
            .build();
        model.addAttribute("userLoginDto", userLoginDto);
        return "loginForm";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute @Valid UserLoginDto userLoginDto, Model model) {
        if (!userService.findUserIsVisibleByAccountId(userLoginDto.getAccountId()) || userLoginDto.getIsVisible() == null) {
            throw new UserException();
        }
        User user = userService.loginUser(userLoginDto);
        model.addAttribute("user", user);
        return "redirect:/";
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable("userId") Integer userId) {
        userService.updateVisibleUser(userId, false);
        userService.updateUserRole(userId, "정지");
        return "redirect:/";
    }

    @PatchMapping("/admin/{accountId}")
    public String updateUser(@PathVariable("accountId") String accountId, @RequestParam("roleName") String roleName) {
        UserDto userDto = userService.getUserById(accountId);
        userService.updateUserRole(userDto.getId(), roleName);
        return "redirect:/admin";
    }

}
