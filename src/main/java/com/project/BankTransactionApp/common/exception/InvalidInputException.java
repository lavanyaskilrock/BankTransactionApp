package com.project.BankTransactionApp.common.exception;

public class InvalidInputException extends RuntimeException{
    public InvalidInputException(String message){
        super(message);
    }
}
