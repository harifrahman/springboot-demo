package com.example.springbootdemo.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ValidationService {
    @Autowired
    private Validator validator;

    public void validate(Object request) {
        Set<ConstraintViolation<Object>> constrainViolations = validator.validate(request);
        if (constrainViolations.size() != 0) {
            throw new ConstraintViolationException(constrainViolations);
        }
    }
}
