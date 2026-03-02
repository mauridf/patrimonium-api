package br.com.patrimonium.property.repository;

import br.com.patrimonium.property.entity.PropertyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PropertyRepository extends JpaRepository<PropertyEntity, UUID> {

    List<PropertyEntity> findByOwnerId(UUID userId);

    List<PropertyEntity> findByCityAndState(String city, String state);
}