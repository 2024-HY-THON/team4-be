package com.example.hython.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 200 : 요청 성공
     */
    SUCCESS(true, HttpStatus.OK.value(), "요청에 성공하였습니다."),


    /**
     * 400 : Request, Response 오류
     */
    // 이미 가입된 이메일임
    DUPLICATED_EMAIL(false, HttpStatus.BAD_REQUEST.value(), "중복된 이메일입니다."),
    EXPIRED_JWT(false, HttpStatus.BAD_REQUEST.value(), "만료된 토큰입니다."),
    _INVALID_JWT(false, HttpStatus.BAD_REQUEST.value(), "유효하지 않은 토큰입니다."),
    _UNSUPPORTED_JWT(false, HttpStatus.BAD_REQUEST.value(), "지원되지 않는 토큰입니다."),
    _EMPTY_JWT(false, HttpStatus.BAD_REQUEST.value(), "토큰이 비어있습니다."),
    TOKEN_NOT_EXIST(false, HttpStatus.BAD_REQUEST.value(), "토큰이 존재하지 않습니다."),
    NOT_FOUND_MEMBER(false, HttpStatus.BAD_REQUEST.value(), "존재하지 않는 회원입니다."),
    INVALID_PASSWORD(false, HttpStatus.BAD_REQUEST.value(), "비밀번호가 일치하지 않습니다."),
    ADD_RECIPE_FAIL(false, HttpStatus.BAD_REQUEST.value(), "최대 3개의 레시피만 추가할 수 있습니다."),
    DUPLICATED_RECIPE(false, HttpStatus.BAD_REQUEST.value(), "이미 추가된 레시피입니다."),
    NOT_EXIST_RECIPE(false, HttpStatus.BAD_REQUEST.value(), "존재하지 않는 레시피입니다."),
    NOT_FOUND_RECIPE(false, HttpStatus.BAD_REQUEST.value(), "레시피가 존재하지 않습니다."),



    /**
     * 500 :  Database, Server 오류
     */
    MODIFY_FAIL_MEMO(false,HttpStatus.INTERNAL_SERVER_ERROR.value(),"메모 수정 실패"),

    UNEXPECTED_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "예상치 못한 에러가 발생했습니다.");


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}