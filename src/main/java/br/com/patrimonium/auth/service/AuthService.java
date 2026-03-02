package br.com.patrimonium.auth.service;

import br.com.patrimonium.auth.dto.LoginRequest;
import br.com.patrimonium.auth.dto.LoginResponse;
import br.com.patrimonium.auth.jwt.JwtService;
import br.com.patrimonium.user.entity.UserEntity;
import br.com.patrimonium.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {

        UserEntity user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!encoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Credenciais inválidas");
        }

        String token = jwtService.generateToken(user.getId(), user.getEmail(), user.getRole());
        return new LoginResponse(token);
    }
}