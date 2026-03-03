package br.com.patrimonium.person.dto;

import br.com.patrimonium.person.enums.PersonType;

import java.time.LocalDate;

public record PersonCreateRequest(
        PersonType type,
        String fullName,
        String document,
        String email,
        String phone,
        LocalDate birthDate
) {}