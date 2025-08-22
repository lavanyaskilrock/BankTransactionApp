package com.project.BankTransactionApp.common.exception;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(String msg) {
        super(msg);
    }
}
