package br.com.patrimonium.property.dto;

public record PropertyCreateRequest(
        String name,
        String type,
        String purpose,

        String address,
        String city,
        String state,
        String zipCode,

        Integer bedrooms,
        Integer bathrooms,
        Integer garageSpots,
        Double areaTotal,
        Double areaBuilt,
        Boolean furnished,

        Double valueSale,
        Double valueRent,

        String description
) {}