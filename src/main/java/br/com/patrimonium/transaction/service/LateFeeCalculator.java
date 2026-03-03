package br.com.patrimonium.transaction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class LateFeeCalculator {

    public BigDecimal calculatePenalty(
            BigDecimal amount,
            BigDecimal finePercentage
    ) {

        if (finePercentage == null) {
            return BigDecimal.ZERO;
        }

        return amount
                .multiply(finePercentage)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateInterest(
            BigDecimal amount,
            BigDecimal interestPercentage,
            long daysLate
    ) {

        if (interestPercentage == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal monthlyRate =
                interestPercentage.divide(BigDecimal.valueOf(100), 6, RoundingMode.HALF_UP);

        BigDecimal dailyRate =
                monthlyRate.divide(BigDecimal.valueOf(30), 8, RoundingMode.HALF_UP);

        return amount
                .multiply(dailyRate)
                .multiply(BigDecimal.valueOf(daysLate))
                .setScale(2, RoundingMode.HALF_UP);
    }
}