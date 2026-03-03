package br.com.patrimonium.property.service;

import br.com.patrimonium.property.dto.PropertyDashboardDto;
import br.com.patrimonium.property.entity.PropertyEntity;
import br.com.patrimonium.property.repository.PropertyRepository;
import br.com.patrimonium.transaction.repository.FinancialTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PropertyDashboardService {

    private final PropertyRepository propertyRepository;
    private final FinancialTransactionRepository transactionRepository;

    public PropertyDashboardDto getDashboard(UUID propertyId, int month, int year) {

        PropertyEntity property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.plusMonths(1);

        BigDecimal income = transactionRepository.sumIncomeByProperty(propertyId, start, end);
        BigDecimal expenses = transactionRepository.sumExpenseByProperty(propertyId, start, end);

        income = income == null ? BigDecimal.ZERO : income;
        expenses = expenses == null ? BigDecimal.ZERO : expenses;

        BigDecimal net = income.subtract(expenses);

        BigDecimal roi = BigDecimal.ZERO;
        BigDecimal yield = BigDecimal.ZERO;

        if (property.getEstimatedValue() != null &&
                property.getEstimatedValue().compareTo(BigDecimal.ZERO) > 0) {

            roi = net.divide(property.getEstimatedValue(), 4, RoundingMode.HALF_UP);
            yield = property.getRentValue() == null
                    ? BigDecimal.ZERO
                    : property.getRentValue()
                    .divide(property.getEstimatedValue(), 4, RoundingMode.HALF_UP);
        }

        return PropertyDashboardDto.builder()
                .propertyId(property.getId())
                .propertyName(property.getName())
                .estimatedValue(property.getEstimatedValue())
                .rentValue(property.getRentValue())
                .totalIncome(income)
                .totalExpenses(expenses)
                .netProfit(net)
                .roi(roi)
                .yield(yield)
                .profitable(net.compareTo(BigDecimal.ZERO) > 0)
                .build();
    }
}