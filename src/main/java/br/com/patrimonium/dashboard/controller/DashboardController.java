package br.com.patrimonium.dashboard.controller;

import br.com.patrimonium.dashboard.dto.DashboardResponse;
import br.com.patrimonium.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/dashboard")
@PreAuthorize("hasAnyRole('ADMIN','OWNER','USER')")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService service;

    @GetMapping
    public DashboardResponse get(
            @RequestParam UUID propertyId,
            @RequestParam int month,
            @RequestParam int year
    ) {
        return service.get(propertyId, month, year);
    }
}