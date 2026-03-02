package br.com.patrimonium.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserCreateRequest(
        @Email String email,
        @NotBlank String password,
        String name
) {}