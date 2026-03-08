package br.com.patrimonium.person.controller;

import br.com.patrimonium.person.dto.*;
import br.com.patrimonium.person.enums.PersonType;
import br.com.patrimonium.person.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import java.util.UUID;

@RestController
@RequestMapping("/api/persons")
@PreAuthorize("hasAnyRole('ADMIN','OWNER','USER')")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @PostMapping
    public PersonResponse create(@RequestBody PersonCreateRequest request) {
        return personService.create(request);
    }

    @GetMapping("/{id}")
    public PersonResponse findById(@PathVariable UUID id) {
        return personService.findById(id);
    }

    @PutMapping("/{id}")
    public PersonResponse update(@PathVariable UUID id,
                                 @RequestBody PersonCreateRequest request) {
        return personService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        personService.delete(id);
    }

    @GetMapping
    public Page<PersonResponse> search(
            @RequestParam(required = false) PersonType type,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean active,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return personService.search(type, name, active, pageable);
    }
}