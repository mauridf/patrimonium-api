package br.com.patrimonium.property.dto;

import java.math.BigDecimal;

public record PropertyCreateRequest(
        String name,
        String type,
        String purpose,
        String category,

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
        Boolean availableForSale
) {}