package br.com.patrimonium.transaction.entity;

import br.com.patrimonium.property.entity.PropertyEntity;
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
    private String type; // INCOME ou EXPENSE

    @Column(nullable = false)
    private LocalDate transactionDate;

    private String description;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
    }
}