package br.com.patrimonium.contract.dto;

import br.com.patrimonium.contract.enums.ContractStatus;
import br.com.patrimonium.contract.enums.ContractType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ContractResponse(
        UUID id,
        UUID propertyId,
        UUID landlordId,
        UUID tenantId,
        UUID guarantorId,
        ContractType type,
        ContractStatus status,
        BigDecimal contractValue,
        BigDecimal monthlyValue,
        LocalDate startDate,
        LocalDate endDate,
        Boolean active
) {}
