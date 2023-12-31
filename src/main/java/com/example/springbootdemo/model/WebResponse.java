package com.example.springbootdemo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WebResponse<T> {
    private boolean success;
    private T data;
    private String error;
}
