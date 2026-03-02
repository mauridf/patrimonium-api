package br.com.patrimonium.user.service;

import br.com.patrimonium.user.dto.UserCreateRequest;
import br.com.patrimonium.user.dto.UserResponse;
import br.com.patrimonium.user.entity.UserEntity;
import br.com.patrimonium.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public UserResponse create(UserCreateRequest request) {

        UserEntity user = UserEntity.builder()
                .email(request.email())
                .password(request.password()) // depois entra criptografia
                .name(request.name())
                .build();

        UserEntity saved = repository.save(user);

        return new UserResponse(
                saved.getId(),
                saved.getEmail(),
                saved.getName(),
                saved.getActive(),
                saved.getCreatedAt()
        );
    }
}