package com.example.springbootdemo.model.users.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterUserRequest {
    @NotBlank
    @Size(max = 100)
    public String username;

    @NotBlank
    @Size(max = 100)
    public String password;

    @NotBlank
    @Size(max = 100)
    public String name;
}
