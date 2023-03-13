package com.folcode.super_hero_api.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiExceptionResponse> exceptionHandler(Exception exception, HttpServletRequest request) {
        ApiExceptionResponse error = new ApiExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getClass().toString(), exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SuperHeroNotFoundExceptions.class)
    public ResponseEntity<ApiExceptionResponse> superHeroNotFoundExceptionsHandler(SuperHeroNotFoundExceptions exception, HttpServletRequest request) {
        ApiExceptionResponse error = new ApiExceptionResponse(HttpStatus.NOT_FOUND, exception.getClass().toString(), exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
