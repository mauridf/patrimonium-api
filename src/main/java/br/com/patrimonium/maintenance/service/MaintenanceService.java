package br.com.patrimonium.maintenance.service;

import br.com.patrimonium.maintenance.entity.MaintenanceEntity;
import br.com.patrimonium.maintenance.enums.MaintenanceStatus;
import br.com.patrimonium.maintenance.repository.MaintenanceRepository;
import br.com.patrimonium.property.repository.PropertyRepository;
import br.com.patrimonium.property.service.PropertyFinancialCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MaintenanceService {

    private final MaintenanceRepository repository;
    private final PropertyRepository propertyRepository;
    private final PropertyFinancialCalculator calculator;

    public void create(UUID propertyId, String title,
                       String description,
                       BigDecimal cost) {

        var property = propertyRepository.findById(propertyId)
                .orElseThrow();

        var maintenance = MaintenanceEntity.builder()
                .id(UUID.randomUUID())
                .property(property)
                .title(title)
                .description(description)
                .cost(cost)
                .status(MaintenanceStatus.OPEN)
                .openDate(LocalDate.now())
                .createdAt(LocalDateTime.now())
                .build();

        repository.save(maintenance);

        // Impacta no ROI
        calculator.recalculate(property);
        propertyRepository.save(property);
    }

    public void complete(UUID id) {

        var maintenance = repository.findById(id).orElseThrow();

        maintenance.setStatus(MaintenanceStatus.DONE);
        maintenance.setCompletionDate(LocalDate.now());

        repository.save(maintenance);
    }
}
