package br.com.patrimonium.person.controller;

import br.com.patrimonium.person.dto.*;
import br.com.patrimonium.person.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/persons")
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
}