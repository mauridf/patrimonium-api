package br.com.patrimonium.transaction.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class TransactionCreateRequest {

    private UUID propertyId;
    private BigDecimal amount;
    private String type;
    private LocalDate transactionDate;
    private String description;
}