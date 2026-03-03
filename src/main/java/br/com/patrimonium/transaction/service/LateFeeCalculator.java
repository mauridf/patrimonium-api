package br.com.patrimonium.transaction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@RequiredArgsConstructor
public class LateFeeCalculator {

    public BigDecimal calculatePenalty(BigDecimal amount) {
        return amount.multiply(BigDecimal.valueOf(0.02));
    }

    public BigDecimal calculateInterest(BigDecimal amount, long daysLate) {

        BigDecimal dailyRate =
                BigDecimal.valueOf(0.01)
                        .divide(BigDecimal.valueOf(30), 6, RoundingMode.HALF_UP);

        return amount
                .multiply(dailyRate)
                .multiply(BigDecimal.valueOf(daysLate))
                .setScale(2, RoundingMode.HALF_UP);
    }
}
