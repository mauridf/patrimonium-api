package br.com.patrimonium.property.dto;

import java.math.BigDecimal;

public record PortfolioDashboardResponse(
        Long totalProperties,
        BigDecimal totalEstimatedValue,
        BigDecimal totalIncome,
        BigDecimal totalExpenses,
        BigDecimal netProfit,
        BigDecimal averageRoi,
        BigDecimal averageYield
) {}
