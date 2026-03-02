package br.com.patrimonium.property.dto;

import br.com.patrimonium.property.enums.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PropertyResponse(
        UUID id,
        String name,
        PropertyType type,
        PropertyPurpose purpose,
        PropertyCategory category,

        String address,
        String number,
        String city,
        String state,
        String zipCode,

        Integer bedrooms,
        Integer bathrooms,
        Integer suites,
        Integer parkingSpots,
        BigDecimal totalArea,
        BigDecimal builtArea,
        Integer floor,
        Boolean furnished,
        String description,
        BigDecimal estimatedValue,
        BigDecimal rentValue,
        BigDecimal condoFee,
        BigDecimal iptu,
        Boolean availableForRent,
        Boolean availableForSale,
        LocalDateTime createdAt
) {}