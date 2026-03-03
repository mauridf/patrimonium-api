package br.com.patrimonium.scheduler;

import br.com.patrimonium.contract.repository.ContractRepository;
import br.com.patrimonium.property.service.PropertyFinancialCalculator;
import br.com.patrimonium.transaction.entity.FinancialTransaction;
import br.com.patrimonium.transaction.enums.TransactionStatus;
import br.com.patrimonium.transaction.enums.TransactionType;
import br.com.patrimonium.transaction.repository.FinancialTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MonthlyRentGeneratorScheduler {

    private final ContractRepository contractRepository;
    private final FinancialTransactionRepository transactionRepository;
    private final PropertyFinancialCalculator calculator;

    @Scheduled(cron = "0 0 2 1 * ?")
    // todo dia 1 às 02:00
    public void generateMonthlyRent() {

        LocalDate now = LocalDate.now();
        LocalDate startOfMonth = now.withDayOfMonth(1);
        LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth());

        var contracts = contractRepository.findByActiveTrue();

        for (var contract : contracts) {

            UUID propertyId = contract.getProperty().getId();

            boolean alreadyExists =
                    transactionRepository
                            .existsByPropertyIdAndTypeAndTransactionDateBetween(
                                    propertyId,
                                    TransactionType.RENT,
                                    startOfMonth,
                                    endOfMonth
                            );

            if (alreadyExists) continue;

            LocalDate dueDate = now.withDayOfMonth(
                    Math.min(contract.getDueDay(), now.lengthOfMonth())
            );

            FinancialTransaction rent = FinancialTransaction.builder()
                    .property(contract.getProperty())
                    .amount(contract.getMonthlyValue())
                    .type(TransactionType.RENT)
                    .status(TransactionStatus.PENDING)
                    .transactionDate(dueDate)
                    .paymentDate(null)
                    .description("Aluguel mensal")
                    .build();

            transactionRepository.save(rent);

            calculator.recalculate(contract.getProperty());
        }
    }
}
