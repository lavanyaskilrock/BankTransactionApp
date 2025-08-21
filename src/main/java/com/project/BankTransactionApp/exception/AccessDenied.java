package com.project.BankTransactionApp.exception;

public class AccessDenied extends RuntimeException{
    public AccessDenied(String msg){
        super(msg);
    }
}
