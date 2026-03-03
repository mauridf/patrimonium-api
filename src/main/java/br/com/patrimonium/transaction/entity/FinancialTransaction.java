package br.com.patrimonium.transaction.entity;

import br.com.patrimonium.property.entity.PropertyEntity;
import br.com.patrimonium.transaction.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "financial_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinancialTransaction {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private PropertyEntity property;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private TransactionType type; // INCOME ou EXPENSE

    @Column(name = "transaction_date", nullable = false)
    private LocalDate transactionDate;

    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;

    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private Boolean paid;

    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
    }
}