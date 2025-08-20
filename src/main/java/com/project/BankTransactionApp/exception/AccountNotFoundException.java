package com.project.BankTransactionApp.exception;

public class AccountNotFoundException extends RuntimeException{
    private String message;
    public AccountNotFoundException(String msg) {
        super(msg);
        this.message = msg;
    }
}
