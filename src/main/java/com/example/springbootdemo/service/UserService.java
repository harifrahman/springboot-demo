package com.example.springbootdemo.service;

import com.example.springbootdemo.entity.User;
import com.example.springbootdemo.model.users.RegisterUserRequest;
import com.example.springbootdemo.repository.UserRepository;
import com.example.springbootdemo.security.BCrypt;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Validator validator;

    @Transactional
    public void register(RegisterUserRequest userRequest) {
        Set<ConstraintViolation<RegisterUserRequest>> constrainViolations = validator.validate(userRequest);

        if (constrainViolations.size() != 0) {
            throw new ConstraintViolationException(constrainViolations);
        }

        if (userRepository.existsById(userRequest.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username not available");
        }

        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setName(userRequest.getName());
        user.setPassword(encryptPassword(userRequest));

        userRepository.save(user);
    }

    private static String encryptPassword(RegisterUserRequest userRequest) {
        return BCrypt.hashpw(userRequest.getPassword(), BCrypt.gensalt());
    }
}
