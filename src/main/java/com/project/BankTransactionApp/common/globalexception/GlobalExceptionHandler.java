package com.project.BankTransactionApp.common.globalexception;

import com.project.BankTransactionApp.common.exception.InvalidCredentialsException;
import com.project.BankTransactionApp.common.exception.InvalidInputException;
import com.project.BankTransactionApp.common.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException ex) {
        return new ResponseEntity<>(Map.of("error", ex.getMessage(), "status", 404), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<?> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        return new ResponseEntity<>(Map.of("error", ex.getMessage(), "status", 401), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<?> handleInvalidInput(InvalidInputException ex) {
        return new ResponseEntity<>(Map.of("error", ex.getMessage(), "status",400), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleBadRole(HttpMessageNotReadableException ex) {
        if (ex.getMessage().contains("Role")) {
            return ResponseEntity.badRequest().body("Invalid role. Use: USER or ADMIN");
        }
        return ResponseEntity.badRequest().body("Invalid input");
    }

}