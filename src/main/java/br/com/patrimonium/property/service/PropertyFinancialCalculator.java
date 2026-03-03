package br.com.patrimonium.property.service;

import br.com.patrimonium.property.entity.PropertyEntity;
import br.com.patrimonium.transaction.repository.FinancialTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PropertyFinancialCalculator {

    private final FinancialTransactionRepository repository;

    public void recalculate(PropertyEntity property) {

        UUID propertyId = property.getId();

        BigDecimal income = repository.sumTotalIncome(propertyId);
        BigDecimal expense = repository.sumTotalExpense(propertyId);
        BigDecimal maintenanceCost = repository.sumByProperty(property.getId());

        income = income == null ? BigDecimal.ZERO : income;
        expense = expense == null ? BigDecimal.ZERO : expense;
        maintenanceCost = maintenanceCost == null ? BigDecimal.ZERO : maintenanceCost;

        BigDecimal roi = income.subtract(expense).subtract(maintenanceCost);

        BigDecimal net = income.subtract(expense);

        if (property.getEstimatedValue() != null &&
                property.getEstimatedValue().compareTo(BigDecimal.ZERO) > 0) {

            roi = net.divide(property.getEstimatedValue(), 6, RoundingMode.HALF_UP);

            BigDecimal yield = property.getRentValue() == null
                    ? BigDecimal.ZERO
                    : property.getRentValue()
                    .divide(property.getEstimatedValue(), 6, RoundingMode.HALF_UP);

            property.setRoi(roi);
            property.setYield(yield);
        }
    }
}