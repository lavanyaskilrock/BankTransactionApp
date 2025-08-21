package com.project.BankTransactionApp.exception;

public class InvalidAmountException extends RuntimeException{
    private String message;
    public InvalidAmountException(String msg) {
        super(msg);
        this.message = msg;
    }
}
