package com.example.demo.common;

public record ErrorResponse(
        String errorCode,
        String message
) {
    public static ErrorResponse from(BusinessException e) {
        return new ErrorResponse(e.getCode(), e.getMessage());
    }
}
