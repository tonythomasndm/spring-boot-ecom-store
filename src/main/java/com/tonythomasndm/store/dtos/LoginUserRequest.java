package com.tonythomasndm.store.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginUserRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid one")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;
}
