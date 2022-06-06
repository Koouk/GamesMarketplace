package com.example.tiwpr.exception;

public class WrongVersionException extends RuntimeException{

    public WrongVersionException(String e) {
        super(e);
    }

}