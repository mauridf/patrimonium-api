package br.com.patrimonium.transaction.dto;

import br.com.patrimonium.transaction.entity.FinancialTransaction;
import br.com.patrimonium.transaction.enums.TransactionType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class TransactionResponse {

    private UUID id;
    private UUID propertyId;
    private BigDecimal amount;
    private TransactionType type;
    private LocalDate transactionDate;
    private String description;

    public static TransactionResponse from(FinancialTransaction t) {
        return TransactionResponse.builder()
                .id(t.getId())
                .propertyId(t.getProperty().getId())
                .amount(t.getAmount())
                .type(t.getType())
                .transactionDate(t.getTransactionDate())
                .description(t.getDescription())
                .build();
    }
}