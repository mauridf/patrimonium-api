package br.com.patrimonium.user.controller;

import br.com.patrimonium.user.dto.UserCreateRequest;
import br.com.patrimonium.user.dto.UserResponse;
import br.com.patrimonium.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@RequestBody @Valid UserCreateRequest request) {
        return service.create(request);
    }
}