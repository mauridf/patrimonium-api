package br.com.patrimonium.maintenance.dto;

import br.com.patrimonium.maintenance.enums.MaintenanceStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MaintenanceResponse {

    @Setter
    @Getter
    private String id;
    @Setter
    @Getter
    private String propertyId;
    @Getter
    private String title;
    @Getter
    @Setter
    private String description;
    @Setter
    private MaintenanceStatus status;
    @Getter
    @Setter
    private BigDecimal cost;
    @Getter
    @Setter
    private LocalDate openDate;
    @Getter
    @Setter
    private LocalDate completionDate;

    public MaintenanceStatus getStatus() {
        return status;
    }

}