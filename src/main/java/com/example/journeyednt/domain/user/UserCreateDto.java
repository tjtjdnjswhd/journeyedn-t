package com.example.journeyednt.domain.user;

import com.example.journeyednt.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

import static com.example.journeyednt.global.constant.RegExp.ACCOUNT_ID_REGEXP;
import static com.example.journeyednt.global.constant.RegExp.PASSWORD_REGEXP;

@Getter
@Builder
public class UserCreateDto {
    @NotBlank(message = "이름을 입력해주세요")
    private String name;

    @Pattern(message = "대소문자나 숫자를 포함한 5-20자리로 입력하세요.", regexp = ACCOUNT_ID_REGEXP)
    private String accountId;

    @Pattern(message = "대소문자와 숫자, 특수문자를 포함한 8-16자리의 비밀번호를 입력하세요.", regexp = PASSWORD_REGEXP)
    private String passwordHash;

    @NotBlank(message = "닉네임을 입력해주세요")
    private String nickName;

    public static UserCreateDto toDto(User user) {
        return UserCreateDto.builder()
                .name(user.getName())
                .accountId(user.getAccountId())
                .passwordHash(user.getPasswordHash())
                .nickName(user.getNickName())
                .build();
    }

    public User toEntity() {
        return User.builder()
                .name(this.name)
                .accountId(this.accountId)
                .passwordHash(this.passwordHash)
                .nickName(this.nickName)
                .createdAt(LocalDateTime.now())
                .isVisible(true)
                .build();
    }
}
