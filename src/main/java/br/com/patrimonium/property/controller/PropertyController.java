package br.com.patrimonium.property.controller;

import br.com.patrimonium.property.dto.*;
import br.com.patrimonium.property.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService service;

    @PostMapping
    public PropertyResponse create(@RequestBody PropertyCreateRequest request) {
        return service.create(request);
    }

    @GetMapping("/me")
    public List<PropertyResponse> myProperties() {
        return service.listMyProperties();
    }
}