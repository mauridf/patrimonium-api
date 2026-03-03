package br.com.patrimonium.transaction.service;

import br.com.patrimonium.common.dto.PageResponse;
import br.com.patrimonium.common.util.PageMapper;
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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

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

    public PageResponse<TransactionResponse> list(
            UUID propertyId,
            int page,
            int size
    ) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "transactionDate")
        );

        Page<TransactionResponse> result =
                repository
                        .findByPropertyId(propertyId, pageable)
                        .map(this::toResponse);

        return PageMapper.toResponse(result);
    }

    private TransactionResponse toResponse(FinancialTransaction entity) {
        return TransactionResponse.builder()
                .id(entity.getId())
                .propertyId(entity.getProperty().getId())
                .amount(entity.getAmount())
                .type(entity.getType())
                .transactionDate(entity.getTransactionDate())
                .paymentDate(entity.getPaymentDate())
                .description(entity.getDescription())
                .paid(entity.getPaid())
                .build();
    }
}