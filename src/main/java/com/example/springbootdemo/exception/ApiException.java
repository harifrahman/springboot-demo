package com.example.springbootdemo.exception;

public class ApiException extends RuntimeException {
    public ApiException(String msg) {
        super(msg);
    }
}
