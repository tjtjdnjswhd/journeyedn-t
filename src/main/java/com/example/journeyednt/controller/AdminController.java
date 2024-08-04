package com.example.journeyednt.controller;

import com.example.journeyednt.domain.post.PostDto;
import com.example.journeyednt.domain.user.UserDto;
import com.example.journeyednt.entity.Role;
import com.example.journeyednt.service.PostService;
import com.example.journeyednt.service.RoleService;
import com.example.journeyednt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final PostService postService;
    private final RoleService roleService;
    private final UserService userService;

    @GetMapping
    public String index(Model model) {
        List<Role> roles = roleService.getAll();
        model.addAttribute("roles", roles);
        return "adminIndex";
    }

    @PostMapping("/users/role")
    public String updateUserRole(@RequestParam("accountId") String accountId, @RequestParam("roleName") String roleName) {
        userService.updateUserRole(accountId, roleName);
        return "redirect:/admin";
    }

    @PostMapping("/notice")
    public String addNotice(@RequestParam("title") String title, @RequestParam("content") String content, Principal principal) {
        String nickName = principal.getName();
        UserDto loginUser = userService.findByNickname(nickName);
        PostDto notice = postService.createNotice(title, content, loginUser.getId());

        return "redirect:/admin/notice/" + notice.getId();
    }

    @GetMapping("/notice/{id}/edit")
    public String editNotice(@PathVariable Integer id, Model model) {
        PostDto notice = postService.getPostById(id);
        if (!notice.getIsNotice()) {
            return "redirect:/index";
        }

        model.addAttribute("notice", notice);
        return "noticeEdit";
    }

    @PostMapping("/notice/{id}/edit")
    public String editNotice(@PathVariable Integer id, @RequestParam("title") String title, @RequestParam("content") String content) {
        PostDto notice = postService.getPostById(id);
        if (!notice.getIsNotice()) {
            return "redirect:/index";
        }

        postService.updateNotice(id, title, content);

        return "/admin/notice/" + id;
    }

    @GetMapping("/notice/{id}")
    public String getNotice(@PathVariable Integer id, Model model) {
        PostDto notice = postService.getPostById(id);
        if (!notice.getIsNotice()) {
            throw new IllegalArgumentException("공지사항이 아닙니다");
        }

        model.addAttribute("title", notice.getTitle());
        model.addAttribute("content", notice.getContent());

        return "notice";
    }

    @GetMapping("/notice/{id}/delete")
    public String deleteNotice(@PathVariable Integer id, Model model) {
        model.addAttribute("id", id);
        return "deleteNotice";
    }

    @PostMapping("/notice/{id}/delete")
    public String deleteNotice(@PathVariable Integer id) {
        postService.invisiblePost(id);
        return "redirect:/index";
    }
}
