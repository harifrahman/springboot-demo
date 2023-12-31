package com.example.springbootdemo.controller;

import com.example.springbootdemo.entity.User;
import com.example.springbootdemo.model.WebResponse;
import com.example.springbootdemo.model.users.request.RegisterUserRequest;
import com.example.springbootdemo.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
    }

    @Test
    void GivenValidRequestWhenRegisterShouldReturnSuccess() throws Exception {
        RegisterUserRequest request = RegisterUserRequest.builder()
                .name("hakim")
                .username("user-hakim")
                .password("secret")
                .build();

        mockMvc.perform(
                post("/api/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertEquals("OK", response.getData());
            assertTrue(response.isSuccess());
        });
    }

    @Test
    void GivenInvalidRequestWhenRegisterShouldReturnErrorBadRequest() throws Exception {
        RegisterUserRequest request = RegisterUserRequest.builder().build();

        mockMvc.perform(
                post("/api/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getError());
            assertFalse(response.isSuccess());
        });
    }

    @Test
    void GivenInvalidRequestWhenRegisterWithDuplicateUsernameShouldReturnErrorHandleMsg() throws Exception {
        User existingUser = new User();
        existingUser.setPassword("secret-random");
        existingUser.setUsername("user-hakim");
        existingUser.setName("hakim-fastfood");

        userRepository.save(existingUser);

        RegisterUserRequest request = RegisterUserRequest.builder()
                .name("hakim")
                .username("user-hakim")
                .password("secret")
                .build();

        mockMvc.perform(
                post("/api/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getError());
            assertFalse(response.isSuccess());
            assertEquals("Username not available", response.getError());
        });
    }
}
