package com.project.BankTransactionApp.common.globalexception;

import com.project.BankTransactionApp.common.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.Access;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex){
        return new ResponseEntity<>(Map.of("error",ex.getMessage(),"status",401), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<?> handleAccountNotFoundException(AccountNotFoundException ex) {
        return new ResponseEntity<>(Map.of("error", ex.getMessage(), "status",400), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<?> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        return new ResponseEntity<>(Map.of("error", ex.getMessage(), "status", 401), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(InvalidAmountException.class)
    public ResponseEntity<?> handleInvalidAmount(InvalidAmountException ex) {
        return new ResponseEntity<>(Map.of("error", ex.getMessage(), "status",400), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<?> handleInvalidInput(InvalidInputException ex) {
        return new ResponseEntity<>(Map.of("error", ex.getMessage(), "status",400), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException ex) {
        return new ResponseEntity<>(Map.of("error", ex.getMessage(), "status", 404), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<?> handleDuplicateEntry(SQLIntegrityConstraintViolationException ex){
        String message = "Duplicate entry. This value already exists.";
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", message));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "An unexpected error occurred", "status", 500));
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleBadRole(HttpMessageNotReadableException ex) {
        String message = (ex.getMessage() != null && ex.getMessage().contains("Role"))
                ?"Invalid role. Use: USER or ADMIN"
                :   "Invalid input";

        return ResponseEntity.badRequest().body(Map.of("error", message));
    }

}