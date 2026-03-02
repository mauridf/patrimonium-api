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

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository repository;
    private final UserRepository userRepository;

    private UserEntity getLoggedUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var email = auth.getName();
        return userRepository.findByEmail(email).orElseThrow();
    }

    public PropertyResponse create(PropertyCreateRequest request) {

        UserEntity user = getLoggedUser();

        PropertyEntity property = PropertyEntity.builder()
                .owner(user)

                .name(request.name())
                .type(request.type())
                .purpose(request.purpose())

                .address(request.address())
                .city(request.city())
                .state(request.state())
                .zipCode(request.zipCode())

                .bedrooms(request.bedrooms())
                .bathrooms(request.bathrooms())
                .garageSpots(request.garageSpots())
                .areaTotal(request.areaTotal())
                .areaBuilt(request.areaBuilt())
                .furnished(request.furnished())

                .valueSale(request.valueSale())
                .valueRent(request.valueRent())

                .description(request.description())
                .build();

        var saved = repository.save(property);

        return map(saved);
    }

    public List<PropertyResponse> listMyProperties() {
        UserEntity user = getLoggedUser();

        return repository.findByOwnerId(user.getId())
                .stream()
                .map(this::map)
                .toList();
    }

    private PropertyResponse map(PropertyEntity p) {
        return new PropertyResponse(
                p.getId(),
                p.getName(),
                p.getType(),
                p.getPurpose(),

                p.getAddress(),
                p.getCity(),
                p.getState(),
                p.getZipCode(),

                p.getBedrooms(),
                p.getBathrooms(),
                p.getGarageSpots(),
                p.getAreaTotal(),
                p.getAreaBuilt(),
                p.getFurnished(),

                p.getValueSale(),
                p.getValueRent(),

                p.getDescription(),
                p.getCreatedAt()
        );
    }
}