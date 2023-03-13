package com.folcode.super_hero_api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@AllArgsConstructor
public class ApiExceptionResponse {
    private HttpStatus status;
    private String title;
    private String detail;
}
