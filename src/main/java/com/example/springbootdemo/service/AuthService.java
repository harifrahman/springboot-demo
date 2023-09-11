package com.example.springbootdemo.service;

import com.example.springbootdemo.entity.User;
import com.example.springbootdemo.model.users.request.LoginRequest;
import com.example.springbootdemo.model.users.response.TokenResponse;
import com.example.springbootdemo.repository.UserRepository;
import com.example.springbootdemo.security.BCrypt;
import jakarta.transaction.Transactional;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public TokenResponse login(LoginRequest request) {
        validationService.validate(request);

        User user = userRepository.findById(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username & password is not correct"));

        if (isBCryptPasswordValid(request, user)) {
            user.setToken(UUID.randomUUID().toString());
            user.setTokenExpiredAt(nextDays(7));
            userRepository.save(user);

            return TokenResponse.builder()
                    .token(user.getToken())
                    .expiredAt(user.getTokenExpiredAt())
                    .build();
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username password given is wrong");
    }

    private static boolean isBCryptPasswordValid(LoginRequest request, User user) {
        return BCrypt.checkpw(request.getPassword(), user.getPassword());
    }

    private Long nextDays(int days) {
        return System.currentTimeMillis() + (1000L * 60 * 24 * days);
    }
}
