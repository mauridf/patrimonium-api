package br.com.patrimonium.transaction.service;

import br.com.patrimonium.property.entity.PropertyEntity;
import br.com.patrimonium.property.repository.PropertyRepository;
import br.com.patrimonium.property.service.PropertyFinancialCalculator;
import br.com.patrimonium.transaction.dto.TransactionCreateRequest;
import br.com.patrimonium.transaction.dto.TransactionResponse;
import br.com.patrimonium.transaction.entity.FinancialTransaction;
import br.com.patrimonium.transaction.enums.TransactionType;
import br.com.patrimonium.transaction.repository.FinancialTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FinancialTransactionService {

    private final FinancialTransactionRepository repository;
    private final PropertyRepository propertyRepository;
    private final PropertyFinancialCalculator calculator;

    public TransactionResponse create(TransactionCreateRequest request) {

        PropertyEntity property = propertyRepository.findById(request.getPropertyId())
                .orElseThrow(() -> new RuntimeException("Property not found"));

        FinancialTransaction transaction = FinancialTransaction.builder()
                .id(UUID.randomUUID())
                .property(property)
                .amount(request.getAmount())
                .type(TransactionType.INCOME)
                .transactionDate(request.getTransactionDate())
                .paymentDate(request.getPaymentDate())
                .description(request.getDescription())
                .paid(false)
                .build();

        repository.save(transaction);

        calculator.recalculate(property);
        propertyRepository.save(property);

        return TransactionResponse.from(transaction);
    }

    public BigDecimal forecastAnnualIncome(UUID propertyId) {

        BigDecimal avgMonthly =
                repository.averageLast6Months(propertyId);

        if (avgMonthly == null) {
            return BigDecimal.ZERO;
        }

        return avgMonthly.multiply(BigDecimal.valueOf(12));
    }
}