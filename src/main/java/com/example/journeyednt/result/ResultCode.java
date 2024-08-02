package com.example.journeyednt.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
    // user
    USER_REGISTRATION_SUCCESS("U001", "사용자 등록 성공"),
    USER_USERNAME_DUPLICATED("U002", "회원 아이디 중복"),
    USER_USERNAME_NOT_DUPLICATED("U003", "회원 아이디 중복되지 않음"),
    USER_LOGIN_SUCCESS("U004", "회원 로그인 성공"),
    USER_LOGOUT_SUCCESS("U005", "회원 로그아웃 성공"),
    GET_LOGIN_USER_SUCCESS("U006", "로그인 되어있는 회원 조회 성공"),

    // AUTH
    TOKEN_CREATE_SUCCESS("T001", "토큰 생성 성공");

    private final String code;
    private final String message;
}

