package br.com.patrimonium.property.dto;

import java.util.UUID;

public record PropertyImageResponse(
        UUID id,
        String fileName,
        String url,
        String contentType
) {}