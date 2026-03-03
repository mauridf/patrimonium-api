package br.com.patrimonium.transaction.repository;

import br.com.patrimonium.transaction.entity.FinancialTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface FinancialTransactionRepository
        extends JpaRepository<FinancialTransaction, UUID> {

    Page<FinancialTransaction> findByPropertyId(UUID propertyId, Pageable pageable);

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

    @Query("""
        SELECT t FROM FinancialTransaction t
        WHERE t.paid = false
        AND t.transactionDate < :today
    """)
    List<FinancialTransaction> findOverdueTransactions(LocalDate today);

    @Query("""
        SELECT AVG(t.amount)
        FROM FinancialTransaction t
        WHERE t.property.id = :propertyId
        AND t.type = 'INCOME'
        AND t.paymentDate >= :startDate
    """)
    BigDecimal averageLast6Months(UUID propertyId);

    @Query("""
    SELECT COALESCE(SUM(m.cost),0)
    FROM MaintenanceEntity m
    WHERE m.property.id = :propertyId
    AND m.status = 'DONE'
""")
    BigDecimal sumByProperty(UUID propertyId);

    @Query("""
    SELECT COALESCE(SUM(t.amount), 0)
    FROM FinancialTransaction t
    WHERE t.property.id = :propertyId
    AND t.type = br.com.patrimonium.transaction.enums.TransactionType.INCOME
    AND t.transactionDate >= :start
    AND t.transactionDate < :end
""")
    BigDecimal sumMonthlyIncome(UUID propertyId, LocalDate start, LocalDate end);

    @Query("""
    SELECT COALESCE(SUM(t.amount), 0)
    FROM FinancialTransaction t
    WHERE t.property.id = :propertyId
    AND t.type = br.com.patrimonium.transaction.enums.TransactionType.EXPENSE
    AND t.transactionDate >= :start
    AND t.transactionDate < :end
""")
    BigDecimal sumMonthlyExpense(UUID propertyId, LocalDate start, LocalDate end);

    @Query("""
    SELECT COALESCE(SUM(t.amount), 0)
    FROM FinancialTransaction t
    WHERE t.property.id = :propertyId
    AND t.type = br.com.patrimonium.transaction.enums.TransactionType.INCOME
""")
    BigDecimal sumTotalIncome(UUID propertyId);
}