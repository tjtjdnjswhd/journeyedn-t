package com.example.journeyednt.domain.user;

import com.example.journeyednt.domain.post.PostDto;
import com.example.journeyednt.entity.Role;
import com.example.journeyednt.entity.User;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.journeyednt.global.constant.RegExp.ACCOUNT_ID_REGEXP;
import static com.example.journeyednt.global.constant.RegExp.PASSWORD_REGEXP;

@Getter
@Builder
public class UserLoginDto {
    private Integer id;

    @Pattern(message = "형식이 맞지 않습니다.", regexp = ACCOUNT_ID_REGEXP)
    private String accountId;

    @Pattern(message = "비밀번호 형식이 맞지 않습니다.", regexp = PASSWORD_REGEXP)
    private String passwordHash;

    private String name;
    private String nickName;
    private Role role;
    private List<PostDto> post;
    private Boolean isVisible;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    protected UserLoginDto() {
    }

    public static UserLoginDto createEmpty() {
        return new UserLoginDto();
    }

    public static UserLoginDto toDto(User user) {
        return UserLoginDto.builder()
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
                .passwordHash(this.passwordHash)
                .build();
    }
}
