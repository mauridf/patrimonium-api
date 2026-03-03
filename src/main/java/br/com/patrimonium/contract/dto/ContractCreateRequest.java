package br.com.patrimonium.contract.dto;

import br.com.patrimonium.contract.enums.ContractType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ContractCreateRequest(
        UUID propertyId,
        UUID landlordId,
        UUID tenantId,
        UUID guarantorId,
        ContractType type,
        BigDecimal contractValue,
        BigDecimal monthlyValue,
        LocalDate startDate,
        LocalDate endDate,
        BigDecimal finePercentage,
        BigDecimal interestPercentage
) {}
