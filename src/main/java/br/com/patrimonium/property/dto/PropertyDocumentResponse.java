package br.com.patrimonium.property.dto;

import java.util.UUID;

public record PropertyDocumentResponse(
        UUID id,
        String fileName,
        String type,
        String url,
        String contentType
) {}