package br.com.patrimonium.property.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class PropertyDashboardDto {

    private UUID propertyId;
    private String propertyName;

    private BigDecimal estimatedValue;
    private BigDecimal rentValue;

    private BigDecimal totalIncome;
    private BigDecimal totalExpenses;
    private BigDecimal netProfit;

    private BigDecimal roi;
    private BigDecimal yield;

    private boolean profitable;
}