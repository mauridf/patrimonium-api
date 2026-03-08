package br.com.patrimonium.maintenance.service;

import br.com.patrimonium.maintenance.dto.CreateMaintenanceRequest;
import br.com.patrimonium.maintenance.dto.MaintenanceResponse;
import br.com.patrimonium.maintenance.entity.MaintenanceEntity;
import br.com.patrimonium.maintenance.enums.MaintenanceStatus;
import br.com.patrimonium.maintenance.repository.MaintenanceRepository;
import br.com.patrimonium.property.repository.PropertyRepository;
import br.com.patrimonium.property.service.PropertyFinancialCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MaintenanceService {

    private final MaintenanceRepository repository;
    private final PropertyRepository propertyRepository;
    private final PropertyFinancialCalculator calculator;

    public MaintenanceResponse create(CreateMaintenanceRequest request) {

        var property = propertyRepository.findById(
                UUID.fromString(request.getPropertyId())
        ).orElseThrow();

        var maintenance = MaintenanceEntity.builder()
                .id(UUID.randomUUID())
                .property(property)
                .title(request.getDescription())
                .description(request.getDescription())
                .cost(request.getCost())
                .status(MaintenanceStatus.OPEN)
                .openDate(request.getOpenDate())
                .completionDate(request.getCompletionDate())
                .createdAt(LocalDateTime.now())
                .build();

        repository.save(maintenance);

        // recalcula ROI do imóvel
        calculator.recalculate(property);
        propertyRepository.save(property);

        return toResponse(maintenance);
    }

    public List<MaintenanceResponse> findAll() {

        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public MaintenanceResponse findById(String id) {

        var maintenance = repository.findById(UUID.fromString(id))
                .orElseThrow();

        return toResponse(maintenance);
    }

    public void complete(UUID id) {

        var maintenance = repository.findById(id).orElseThrow();

        maintenance.setStatus(MaintenanceStatus.DONE);
        maintenance.setCompletionDate(LocalDate.now());

        repository.save(maintenance);
    }

    private MaintenanceResponse toResponse(MaintenanceEntity entity) {

        var response = new MaintenanceResponse();

        response.setId(entity.getId().toString());
        response.setPropertyId(entity.getProperty().getId().toString());
        response.setDescription(entity.getDescription());
        response.setCost(entity.getCost());
        response.setOpenDate(entity.getOpenDate());
        response.setCompletionDate(entity.getCompletionDate());
        response.setStatus(null);

        return response;
    }
}