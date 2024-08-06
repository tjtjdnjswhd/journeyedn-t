package com.example.journeyednt.domain.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import static com.example.journeyednt.global.constant.RegExp.ACCOUNT_ID_REGEXP;
import static com.example.journeyednt.global.constant.RegExp.PASSWORD_REGEXP;

@Getter
@Setter
public class UserSignup {
    @NotBlank(message = "이름을 입력해주세요")
    private String name;

    @Pattern(message = "대소문자나 숫자를 포함한 5-20자리로 입력하세요.", regexp = ACCOUNT_ID_REGEXP)
    private String accountId;

    @Pattern(message = "대소문자와 숫자, 특수문자를 포함한 8-16자리의 비밀번호를 입력하세요.", regexp = PASSWORD_REGEXP)
    private String password;

    @NotBlank(message = "닉네임을 입력해주세요")
    private String nickName;

    protected UserSignup() {
    }

    public static UserSignup createEmpty() {
        return new UserSignup();
    }
}
