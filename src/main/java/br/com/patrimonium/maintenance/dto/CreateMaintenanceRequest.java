package br.com.patrimonium.maintenance.dto;

import br.com.patrimonium.maintenance.enums.MaintenanceStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class CreateMaintenanceRequest {

    @Setter
    private String propertyId;
    @Setter
    private String title;
    @Setter
    private String description;
    private MaintenanceStatus status;
    @Setter
    private BigDecimal cost;
    @Setter
    private LocalDate openDate;
    @Setter
    private LocalDate completionDate;

    public void setStatus(MaintenanceStatus status) {
        this.status = status;
    }

}