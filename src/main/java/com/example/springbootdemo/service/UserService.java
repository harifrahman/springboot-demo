package com.example.springbootdemo.service;

import com.example.springbootdemo.entity.User;
import com.example.springbootdemo.exception.ApiException;
import com.example.springbootdemo.model.users.RegisterUserRequest;
import com.example.springbootdemo.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            throw new ApiException("Username not available");
        }

        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setName(userRequest.getName());

        // TODO: we need to encrypt this, or user will laugh hard :D
        user.setPassword(userRequest.getPassword());

        userRepository.save(user);
    }
}
