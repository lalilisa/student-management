package com.example.chatapplication.custom.exception;

public class UnAuthorizationException extends RuntimeException{

    Integer statusCode;

    public UnAuthorizationException(String message,  Integer statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
