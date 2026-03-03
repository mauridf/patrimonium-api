package br.com.patrimonium.person.dto;

import br.com.patrimonium.person.enums.PersonType;

import java.time.LocalDate;
import java.util.UUID;

public record PersonResponse(
        UUID id,
        PersonType type,
        String fullName,
        String document,
        String email,
        String phone,
        LocalDate birthDate,
        Boolean active
) {}