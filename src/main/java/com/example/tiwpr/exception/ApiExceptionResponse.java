package com.example.tiwpr.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiExceptionResponse {

    private int status;
    private String message;

    public ApiExceptionResponse(HttpStatus status, String message) {
        this.status = status.value();
        this.message = message;
    }
}
