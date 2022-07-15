package com.example.tiwpr.exception;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.StaleObjectStateException;
import org.hibernate.StaleStateException;
import org.postgresql.util.PSQLException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(value = PSQLException.class)
    @ResponseBody
    public ResponseEntity<?> handlePqslException(PSQLException e) {
        if(e.getMessage().contains("violates unique constraint")) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiExceptionResponse handleBadRequestException(BadRequestException e) {
        return new ApiExceptionResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }


    @ExceptionHandler(value = ResourceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ApiExceptionResponse handleConflictException(Exception e) {
        return new ApiExceptionResponse(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(value = {StaleStateException.class, StaleObjectStateException.class, WrongVersionException.class})
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ResponseBody
    public ApiExceptionResponse handleStaleStateException(Exception e) {
        return new ApiExceptionResponse(HttpStatus.PRECONDITION_FAILED, e.getMessage());
    }

    @ExceptionHandler(value = {EmptyResultDataAccessException.class, ResourceNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiExceptionResponse handleResourceNotFoundException(Exception e) {
        return new ApiExceptionResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(value = {NoEtagException.class})
    @ResponseStatus(HttpStatus.PRECONDITION_REQUIRED)
    @ResponseBody
    public ApiExceptionResponse handleNoEtagException(Exception e) {
        return new ApiExceptionResponse(HttpStatus.PRECONDITION_REQUIRED, e.getMessage());
    }

}
