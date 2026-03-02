package br.com.patrimonium.property.service;

import br.com.patrimonium.property.dto.*;
import br.com.patrimonium.property.entity.PropertyEntity;
import br.com.patrimonium.property.repository.PropertyRepository;
import br.com.patrimonium.user.entity.UserEntity;
import br.com.patrimonium.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository repository;
    private final UserRepository userRepository;

    public PropertyResponse create(PropertyCreateRequest request) {

        var auth = SecurityContextHolder.getContext().getAuthentication();
        var email = auth.getName();

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow();

        PropertyEntity property = PropertyEntity.builder()
                .owner(user)
                .name(request.name())
                .type(request.type())
                .purpose(request.purpose())
                .address(request.address())
                .build();

        var saved = repository.save(property);

        return new PropertyResponse(
                saved.getId(),
                saved.getName(),
                saved.getType(),
                saved.getPurpose(),
                saved.getAddress(),
                saved.getCreatedAt()
        );
    }

    public List<PropertyResponse> listMyProperties() {

        var auth = SecurityContextHolder.getContext().getAuthentication();
        var email = auth.getName();

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow();

        return repository.findByOwnerId(user.getId())
                .stream()
                .map(p -> new PropertyResponse(
                        p.getId(),
                        p.getName(),
                        p.getType(),
                        p.getPurpose(),
                        p.getAddress(),
                        p.getCreatedAt()
                ))
                .toList();
    }
}