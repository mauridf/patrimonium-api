package br.com.patrimonium.property.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record PropertyResponse(
        UUID id,
        String name,
        String type,
        String purpose,
        String address,
        LocalDateTime createdAt
) {}