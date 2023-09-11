package com.example.springbootdemo.model;

import lombok.Data;

@Data
public class WebResponse<T> {
    private boolean success;
    private T data;
    private String error;
}
