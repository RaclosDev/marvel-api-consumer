package com.test.api.marvel.dto.security;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank
    String username;

    @NotBlank
    String password;
}
