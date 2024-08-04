package com.example.journeyednt.domain.user;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import static com.example.journeyednt.global.constant.RegExp.ACCOUNT_ID_REGEXP;
import static com.example.journeyednt.global.constant.RegExp.PASSWORD_REGEXP;

@Getter
public class UserLogin {
    @Pattern(message = "형식이 맞지 않습니다.", regexp = ACCOUNT_ID_REGEXP)
    private String accountId;

    @Pattern(message = "비밀번호 형식이 맞지 않습니다.", regexp = PASSWORD_REGEXP)
    private String password;

    protected UserLogin() {
    }

    public static UserLogin createEmpty() {
        return new UserLogin();
    }
}
