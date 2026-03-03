package br.com.patrimonium.property.controller;

import br.com.patrimonium.property.dto.*;
import br.com.patrimonium.property.service.PropertyDashboardService;
import br.com.patrimonium.property.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/properties")
@PreAuthorize("hasAnyRole('ADMIN','OWNER','USER')")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService service;
    private final PropertyDashboardService propertyDashboardService;

    @PostMapping
    public PropertyResponse create(@RequestBody PropertyCreateRequest request) {
        return service.create(request);
    }

    @GetMapping("/me")
    public List<PropertyResponse> myProperties() {
        return service.listMyProperties();
    }

    @GetMapping("/{id}/dashboard")
    public PropertyDashboardDto dashboard(
            @PathVariable UUID id,
            @RequestParam int month,
            @RequestParam int year) {

        return propertyDashboardService.getDashboard(id, month, year);
    }
}