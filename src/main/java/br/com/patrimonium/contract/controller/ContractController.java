package br.com.patrimonium.contract.controller;

import br.com.patrimonium.contract.dto.*;
import br.com.patrimonium.contract.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService service;

    @PostMapping
    public ContractResponse create(@RequestBody ContractCreateRequest request) {
        return service.create(request);
    }
}
