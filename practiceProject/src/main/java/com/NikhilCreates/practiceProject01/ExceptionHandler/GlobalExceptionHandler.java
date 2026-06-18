package com.NikhilCreates.practiceProject01.ExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.http.HttpResponse;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleWrongCredential(BadCredentialsException ex)
    {
        ErrorResponse errorResponse=new ErrorResponse(LocalDateTime.now(), ex.getMessage(), "Invalid Credentials");
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleVerification(MethodArgumentNotValidException ex)
    {
        ErrorResponse errorResponse=new ErrorResponse(LocalDateTime.now(), ex.getMessage(), "Invalid entries");

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
