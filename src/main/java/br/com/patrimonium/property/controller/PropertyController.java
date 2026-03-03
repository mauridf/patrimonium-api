package br.com.patrimonium.property.controller;

import br.com.patrimonium.property.dto.*;
import br.com.patrimonium.property.entity.PropertyValuation;
import br.com.patrimonium.property.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/properties")
@PreAuthorize("hasAnyRole('ADMIN','OWNER','USER')")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService service;
    private final PropertyDashboardService propertyDashboardService;
    private final PropertyImageService imageService;
    private final PropertyDocumentService documentService;
    private final PropertyValuationService valuationService;

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

    @PostMapping("/{id}/images")
    public void uploadImage(
            @PathVariable UUID id,
            @RequestParam("file") MultipartFile file) throws Exception {

        imageService.upload(id, file);
    }

    @PostMapping("/{id}/documents")
    public void uploadDocument(
            @PathVariable UUID id,
            @RequestParam String type,
            @RequestParam("file") MultipartFile file) throws Exception {

        documentService.upload(id, type, file);
    }

    @GetMapping("/documents/{docId}")
    public ResponseEntity<Resource> download(@PathVariable UUID docId) throws Exception {

        Resource resource = documentService.download(docId);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + resource.getFilename())
                .body(resource);
    }

    @PostMapping("/{id}/valuations")
    public void addValuation(
            @PathVariable UUID id,
            @RequestParam BigDecimal amount,
            @RequestParam LocalDate date,
            @RequestParam(required = false) String notes) {

        valuationService.add(id, amount, date, notes);
    }

    @GetMapping("/{id}/valuations")
    public List<PropertyValuation> history(@PathVariable UUID id) {
        return valuationService.history(id);
    }
}