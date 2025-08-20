package com.project.BankTransactionApp.exception;

public class AccessDenied extends RuntimeException{
    private String message;
    public AccessDenied(String msg){
        super(msg);
        this.message=msg;
    }
}
