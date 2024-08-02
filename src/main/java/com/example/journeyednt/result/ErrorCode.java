package com.example.journeyednt.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // user
    USER_NOT_FOUND(404, "사용자를 찾을 수 없습니다."),
    USER_TYPE_NOT_NONE(400, "사용자 타입을 설정하십시오. (현재: 없음)"),
    USER_NICKNAME_EXISTS(409, "사용자 닉네임이 이미 존재합니다."),
    USER_ACCOUNT_ID_EXISTS(409, "사용자 아이디가 이미 존재합니다."),
    USER_INACTIVE(403, "비활성 사용자."),
    UNAUTHORIZED_ACCESS(401, "권한 없는 접근."),
    ACCESS_DENIED(403, "접근 거부."),
    EXPIRED_ACCESS_TOKEN(403, "만료된 접근 토큰."),
    TOKEN_NOT_NULL(404, "토큰은 null이 될 수 없습니다."),
    WRONG_PASSWORD(401, "비밀번호가 올바르지 않습니다.");

    private Integer status;
    private final String message;

    ErrorCode(int code, String message) {
        this.status = code;
        this.message = message;
    }

}