package com.example.demo.member.exception;

import com.example.demo.common.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AdminErrorCode implements ErrorCode {
    ADMINKEY_UNAUTHORIZED(HttpStatus.BAD_REQUEST, "ADMIN001", "AdminKey가 옳지 않습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private String message;

}
