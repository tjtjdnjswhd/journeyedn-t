package com.example.journeyednt.domain.user;

import com.example.journeyednt.domain.PostDto;
import com.example.journeyednt.entity.Post;
import com.example.journeyednt.entity.Role;

import com.example.journeyednt.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class UserDto {
    private Integer id;
    private String accountId;
    private String passwordHash;
    private String name;
    private String nickName;
    private Role role;
    private List<Post> post;
    private Boolean isVisible;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .accountId(user.getAccountId())
                .passwordHash(user.getPasswordHash())
                .name(user.getName())
                .nickName(user.getNickName())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .isVisible(user.getIsVisible())
                .role(user.getRole())
                .post(user.getPost() != null ?
                        user.getPost().stream()
                                .map(PostDto::fromEntity)
                                .collect(Collectors.toList())
                        : null)
                .build();
    }

    public User toEntity() {
        return User.builder()
                .accountId(this.accountId)
                .isVisible(this.isVisible)
                .role(this.role)
                .build();
    }

}
