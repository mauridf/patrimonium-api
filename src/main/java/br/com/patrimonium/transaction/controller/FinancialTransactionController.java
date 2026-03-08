package br.com.patrimonium.transaction.controller;

import br.com.patrimonium.common.dto.PageResponse;
import br.com.patrimonium.transaction.dto.TransactionCreateRequest;
import br.com.patrimonium.transaction.dto.TransactionResponse;
import br.com.patrimonium.transaction.service.FinancialTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
@PreAuthorize("hasAnyRole('ADMIN','OWNER','USER')")
@RequiredArgsConstructor
public class FinancialTransactionController {

    private final FinancialTransactionService service;

    @PostMapping
    public TransactionResponse create(@RequestBody TransactionCreateRequest request) {
        return service.create(request);
    }

    @GetMapping
    public PageResponse<TransactionResponse> list(
            @RequestParam UUID propertyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return service.list(propertyId, page, size);
    }
}