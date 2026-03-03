package br.com.patrimonium.property.entity;
import jakarta.persistence.*;

import jakarta.persistence.JoinColumn;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "property_valuations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyValuation {

    @Id
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "property_id")
    private PropertyEntity property;

    @Column(name = "valuation_date")
    private LocalDate valuationDate;

    private BigDecimal amount;

    private String notes;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        id = UUID.randomUUID();
        createdAt = LocalDateTime.now();
    }
}
