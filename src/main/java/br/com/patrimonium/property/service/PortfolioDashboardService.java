package br.com.patrimonium.property.service;

import br.com.patrimonium.property.dto.PortfolioDashboardResponse;

import br.com.patrimonium.property.repository.PropertyRepository;
import br.com.patrimonium.transaction.repository.FinancialTransactionRepository;
import br.com.patrimonium.user.entity.UserEntity;
import br.com.patrimonium.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class PortfolioDashboardService {

    private final PropertyRepository propertyRepository;
    private final FinancialTransactionRepository transactionRepository;
    private final UserRepository userRepository;

    private UserEntity getUser() {
        var email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        return userRepository.findByEmail(email).orElseThrow();
    }

    public PortfolioDashboardResponse getDashboard() {

        var user = getUser();

        var properties = propertyRepository.findByOwnerId(user.getId());

        BigDecimal totalEstimated = BigDecimal.ZERO;
        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpense = BigDecimal.ZERO;
        BigDecimal totalRoi = BigDecimal.ZERO;
        BigDecimal totalYield = BigDecimal.ZERO;

        for (var p : properties) {

            totalEstimated = totalEstimated.add(
                    p.getEstimatedValue() == null ? BigDecimal.ZERO : p.getEstimatedValue()
            );

            totalRoi = totalRoi.add(
                    p.getRoi() == null ? BigDecimal.ZERO : p.getRoi()
            );

            totalYield = totalYield.add(
                    p.getYield() == null ? BigDecimal.ZERO : p.getYield()
            );
        }

        totalIncome = transactionRepository.sumAllIncomeByUser(user.getId());
        totalExpense = transactionRepository.sumAllExpenseByUser(user.getId());

        totalIncome = totalIncome == null ? BigDecimal.ZERO : totalIncome;
        totalExpense = totalExpense == null ? BigDecimal.ZERO : totalExpense;

        BigDecimal net = totalIncome.subtract(totalExpense);

        BigDecimal avgRoi = properties.isEmpty() ? BigDecimal.ZERO :
                totalRoi.divide(BigDecimal.valueOf(properties.size()), 6, RoundingMode.HALF_UP);

        BigDecimal avgYield = properties.isEmpty() ? BigDecimal.ZERO :
                totalYield.divide(BigDecimal.valueOf(properties.size()), 6, RoundingMode.HALF_UP);

        return new PortfolioDashboardResponse(
                (long) properties.size(),
                totalEstimated,
                totalIncome,
                totalExpense,
                net,
                avgRoi,
                avgYield
        );
    }
}
