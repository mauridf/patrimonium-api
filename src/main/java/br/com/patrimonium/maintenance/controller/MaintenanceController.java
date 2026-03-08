package br.com.patrimonium.maintenance.controller;

import br.com.patrimonium.maintenance.dto.CreateMaintenanceRequest;
import br.com.patrimonium.maintenance.dto.MaintenanceResponse;
import br.com.patrimonium.maintenance.service.MaintenanceService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/maintenances")
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @PostMapping
    public ResponseEntity<MaintenanceResponse> create(
            @RequestBody CreateMaintenanceRequest request
    ) {
        return ResponseEntity.ok(
                maintenanceService.create(request)
        );
    }

    @GetMapping
    public ResponseEntity<List<MaintenanceResponse>> findAll() {
        return ResponseEntity.ok(
                maintenanceService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceResponse> findById(
            @PathVariable String id
    ) {
        return ResponseEntity.ok(
                maintenanceService.findById(id)
        );
    }

}