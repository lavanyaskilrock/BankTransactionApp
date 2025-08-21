package com.project.BankTransactionApp.exception;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(String msg) {
        super(msg);
    }
}
