package br.com.patrimonium.transaction.controller;

import br.com.patrimonium.transaction.dto.TransactionCreateRequest;
import br.com.patrimonium.transaction.dto.TransactionResponse;
import br.com.patrimonium.transaction.service.FinancialTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class FinancialTransactionController {

    private final FinancialTransactionService service;

    @PostMapping
    public TransactionResponse create(@RequestBody TransactionCreateRequest request) {
        return service.create(request);
    }
}