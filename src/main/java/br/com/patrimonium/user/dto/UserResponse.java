package br.com.patrimonium.user.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String email,
        String name,
        Boolean active,
        LocalDateTime createdAt
) {}