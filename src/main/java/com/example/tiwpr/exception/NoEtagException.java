package com.example.tiwpr.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

public class NoEtagException extends RuntimeException {

    public NoEtagException(String e) {
        super(e);
    }
}

