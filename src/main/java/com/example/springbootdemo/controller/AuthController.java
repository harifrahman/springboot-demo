package com.example.springbootdemo.controller;

import com.example.springbootdemo.model.WebResponse;
import com.example.springbootdemo.model.users.request.LoginRequest;
import com.example.springbootdemo.model.users.response.TokenResponse;
import com.example.springbootdemo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping(
            path = "/auth/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<TokenResponse> login(@RequestBody LoginRequest request) {
        TokenResponse tokenResponse = authService.login(request);
        return WebResponse.<TokenResponse>builder()
                .success(true)
                .data(tokenResponse)
                .build();
    }
}
