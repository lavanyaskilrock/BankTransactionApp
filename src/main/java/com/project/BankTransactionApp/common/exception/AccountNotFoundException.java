package com.project.BankTransactionApp.common.exception;

public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException(String msg) {
        super(msg);
    }
}
