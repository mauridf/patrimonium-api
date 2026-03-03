package br.com.patrimonium.scheduler;

import br.com.patrimonium.contract.repository.ContractRepository;
import br.com.patrimonium.transaction.repository.FinancialTransactionRepository;
import br.com.patrimonium.transaction.service.FinancialTransactionService;
import br.com.patrimonium.transaction.service.LateFeeCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class FinancialPenaltyScheduler {

    private final FinancialTransactionRepository repository;
    private final LateFeeCalculator calculator;
    private final FinancialTransactionService transactionService;
    private final ContractRepository contractRepository;

    @Scheduled(cron = "0 0 1 * * ?") // todo dia 01:00
    public void applyPenalties() {

        var overdue = repository.findOverdueTransactions(LocalDate.now());

        for (var tx : overdue) {

            long daysLate = ChronoUnit.DAYS.between(
                    tx.getTransactionDate(),
                    LocalDate.now()
            );

            if (daysLate <= 0) continue;

            var contractOpt = contractRepository
                    .findByPropertyIdAndActiveTrue(
                            tx.getProperty().getId()
                    );

            if (contractOpt.isEmpty()) continue;

            var contract = contractOpt.get();

            BigDecimal penalty =
                    calculator.calculatePenalty(
                            tx.getAmount(),
                            contract.getFinePercentage()
                    );

            BigDecimal interest =
                    calculator.calculateInterest(
                            tx.getAmount(),
                            contract.getInterestPercentage(),
                            daysLate
                    );

            transactionService.createPenalty(tx, penalty);
            transactionService.createInterest(tx, interest);
        }
    }
}
