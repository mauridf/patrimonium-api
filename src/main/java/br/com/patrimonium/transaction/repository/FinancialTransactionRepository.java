package br.com.patrimonium.transaction.repository;

import br.com.patrimonium.transaction.entity.FinancialTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public interface FinancialTransactionRepository
        extends JpaRepository<FinancialTransaction, UUID> {

    @Query("""
        SELECT SUM(t.amount)
        FROM FinancialTransaction t
        WHERE t.property.id = :propertyId
        AND t.type = 'INCOME'
        AND t.transactionDate >= :start
        AND t.transactionDate < :end
    """)
    BigDecimal sumIncomeByProperty(
            @Param("propertyId") UUID propertyId,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );

    @Query("""
        SELECT SUM(t.amount)
        FROM FinancialTransaction t
        WHERE t.property.id = :propertyId
        AND t.type = 'EXPENSE'
        AND t.transactionDate >= :start
        AND t.transactionDate < :end
    """)
    BigDecimal sumExpenseByProperty(
            @Param("propertyId") UUID propertyId,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );

    @Query("""
    SELECT SUM(t.amount)
    FROM FinancialTransaction t
    WHERE t.property.id = :propertyId
    AND t.type = 'INCOME'
""")
    BigDecimal sumTotalIncome(UUID propertyId);

    @Query("""
    SELECT SUM(t.amount)
    FROM FinancialTransaction t
    WHERE t.property.id = :propertyId
    AND t.type = 'EXPENSE'
""")
    BigDecimal sumTotalExpense(UUID propertyId);

    @Query("""
    SELECT SUM(t.amount)
    FROM FinancialTransaction t
    WHERE t.property.owner.id = :userId
    AND t.type = 'INCOME'
""")
    BigDecimal sumAllIncomeByUser(UUID userId);

    @Query("""
    SELECT SUM(t.amount)
    FROM FinancialTransaction t
    WHERE t.property.owner.id = :userId
    AND t.type = 'EXPENSE'
""")
    BigDecimal sumAllExpenseByUser(UUID userId);
}