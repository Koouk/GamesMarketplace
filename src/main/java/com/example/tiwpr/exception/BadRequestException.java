package com.example.tiwpr.exception;

public class BadRequestException extends RuntimeException {

  public BadRequestException(String e) {
    super(e);
  }
}
