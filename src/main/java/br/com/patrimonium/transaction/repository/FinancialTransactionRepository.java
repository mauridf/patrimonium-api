import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Query("""
    SELECT SUM(t.amount)
    FROM FinancialTransaction t
    WHERE t.property.id = :propertyId
    AND t.type = 'INCOME'
    AND t.transactionDate >= :start
    AND t.transactionDate < :end
""")
BigDecimal sumIncomeByProperty(UUID propertyId, LocalDate start, LocalDate end);

@Query("""
    SELECT SUM(t.amount)
    FROM FinancialTransaction t
    WHERE t.property.id = :propertyId
    AND t.type = 'EXPENSE'
    AND t.transactionDate >= :start
    AND t.transactionDate < :end
""")
BigDecimal sumExpenseByProperty(UUID propertyId, LocalDate start, LocalDate end);