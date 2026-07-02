package com.valorantassistant.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @NotBlank(message = "username is required")
    @Size(min = 3, max = 64, message = "username length must be between 3 and 64")
    String username,

    @NotBlank(message = "email is required")
    @Email(message = "email must be valid")
    @Size(max = 128, message = "email length must be less than or equal to 128")
    String email,

    @Size(max = 64, message = "displayName length must be less than or equal to 64")
    String displayName,

    @NotBlank(message = "password is required")
    @Size(min = 8, max = 64, message = "password length must be between 8 and 64")
    String password
) {
}
