package br.com.patrimonium.property.repository;

import br.com.patrimonium.property.entity.PropertyEntity;
import br.com.patrimonium.property.entity.PropertyValuation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PropertyValuationRepository
        extends JpaRepository<PropertyValuation, UUID> {

    List<PropertyValuation> findByPropertyIdOrderByValuationDateAsc(UUID propertyId);
}
