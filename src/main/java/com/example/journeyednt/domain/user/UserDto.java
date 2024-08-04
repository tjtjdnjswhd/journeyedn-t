package com.example.journeyednt.domain.user;

import com.example.journeyednt.entity.Role;

import com.example.journeyednt.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserDto {
    private Integer id;
    private String accountId;
    private String passwordHash;
    private String name;
    private String nickName;
    private Role role;
    private Boolean isVisible;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserDto fromEntity(User user) {
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
