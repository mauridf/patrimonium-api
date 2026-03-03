package br.com.patrimonium.maintenance.entity;

import br.com.patrimonium.maintenance.enums.MaintenanceStatus;
import br.com.patrimonium.property.entity.PropertyEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "maintenances")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceEntity {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private PropertyEntity property;

    private String title;

    @Column(length = 1000)
    private String description;

    private BigDecimal cost;

    @Enumerated(EnumType.STRING)
    private MaintenanceStatus status;

    @Column(name="open_date")
    private LocalDate openDate;
    @Column(name="completion_date")
    private LocalDate completionDate;

    @Column(name="create_at")
    private LocalDateTime createdAt;
}
