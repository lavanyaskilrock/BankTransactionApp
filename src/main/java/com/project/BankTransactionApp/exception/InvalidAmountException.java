package com.project.BankTransactionApp.exception;

public class InvalidAmountException extends RuntimeException{
    public InvalidAmountException(String msg) {
        super(msg);
    }
}
