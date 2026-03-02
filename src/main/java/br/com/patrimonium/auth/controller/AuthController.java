package br.com.patrimonium.auth.controller;

import br.com.patrimonium.auth.dto.LoginRequest;
import br.com.patrimonium.auth.dto.LoginResponse;
import br.com.patrimonium.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return service.login(request);
    }
}