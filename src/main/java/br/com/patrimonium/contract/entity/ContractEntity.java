package br.com.patrimonium.contract.entity;

import br.com.patrimonium.contract.enums.*;
import br.com.patrimonium.person.entity.PersonEntity;
import br.com.patrimonium.property.entity.PropertyEntity;
import br.com.patrimonium.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "contracts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractEntity {

    @Id
    private UUID id;

    @ManyToOne(optional = false)
    private UserEntity user;

    @ManyToOne(optional = false)
    private PropertyEntity property;

    @ManyToOne(optional = false)
    @JoinColumn(name = "landlord_id")
    private PersonEntity landlord;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tenant_id")
    private PersonEntity tenant;

    @ManyToOne
    @JoinColumn(name = "guarantor_id")
    private PersonEntity guarantor;

    @Enumerated(EnumType.STRING)
    private ContractType type;

    @Enumerated(EnumType.STRING)
    private ContractStatus status;

    @Column(name = "contract_value")
    private BigDecimal contractValue;
    @Column(name = "monthly_value")
    private BigDecimal monthlyValue;

    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "fine_percentage")
    private BigDecimal finePercentage;
    @Column(name = "interest_percentage")
    private BigDecimal interestPercentage;

    private Boolean active;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}