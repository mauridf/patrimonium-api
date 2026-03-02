package br.com.patrimonium.property.dto;

public record PropertyCreateRequest(
        String name,
        String type,
        String purpose,
        String address
) {}