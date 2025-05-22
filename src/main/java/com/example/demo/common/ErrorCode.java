package com.example.demo.common;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    HttpStatus getStatus();

    String getCode();

    String getMessage();
}
