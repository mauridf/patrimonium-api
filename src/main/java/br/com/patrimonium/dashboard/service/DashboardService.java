package br.com.patrimonium.dashboard.service;

import br.com.patrimonium.dashboard.dto.DashboardResponse;
import br.com.patrimonium.property.entity.PropertyEntity;
import br.com.patrimonium.property.repository.PropertyRepository;
import br.com.patrimonium.transaction.repository.FinancialTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final FinancialTransactionRepository transactionRepository;
    private final PropertyRepository propertyRepository;

    public DashboardResponse get(UUID propertyId, int month, int year) {

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.plusMonths(1);

        BigDecimal monthlyIncome =
                transactionRepository.sumMonthlyIncome(propertyId, start, end);

        BigDecimal monthlyExpense =
                transactionRepository.sumMonthlyExpense(propertyId, start, end);

        BigDecimal totalIncome =
                transactionRepository.sumTotalIncome(propertyId);

        BigDecimal totalExpense =
                transactionRepository.sumTotalExpense(propertyId);

        BigDecimal monthlyNet = monthlyIncome.subtract(monthlyExpense);

        PropertyEntity property =
                propertyRepository.findById(propertyId)
                        .orElseThrow();

        return DashboardResponse.builder()
                .monthlyIncome(monthlyIncome)
                .monthlyExpense(monthlyExpense)
                .monthlyNet(monthlyNet)
                .totalIncome(totalIncome)
                .totalExpense(totalExpense)
                .roi(property.getRoi())
                .yield(property.getYield())
                .build();
    }
}