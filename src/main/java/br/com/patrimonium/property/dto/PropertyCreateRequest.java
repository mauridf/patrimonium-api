package br.com.patrimonium.property.dto;

import java.math.BigDecimal;
import br.com.patrimonium.property.enums.*;

public record PropertyCreateRequest(
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
        Boolean availableForSale
) {}