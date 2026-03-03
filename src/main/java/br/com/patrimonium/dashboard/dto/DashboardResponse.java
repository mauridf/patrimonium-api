package br.com.patrimonium.dashboard.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class DashboardResponse {

    private BigDecimal monthlyIncome;
    private BigDecimal monthlyExpense;
    private BigDecimal monthlyNet;

    private BigDecimal totalIncome;
    private BigDecimal totalExpense;

    private BigDecimal roi;
    private BigDecimal yield;
}