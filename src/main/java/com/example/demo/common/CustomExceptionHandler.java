package com.example.demo.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class CustomExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> businessExceptionHandle(BusinessException e){
        String code = e.getCode();
        String message = e.getMessage();
        HttpStatus status = e.getHttpStatus();

        log.error("code = {}, message = {}", code, message);
        ErrorResponse errorResponse = ErrorResponse.from(e);

        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult()
                .getFieldErrors()
                .forEach(
                        error -> {
                            errors.put(error.getField(), error.getDefaultMessage());
                        });
        return ResponseEntity.status(e.getStatusCode()).body(errors);
    }
}
