package br.com.patrimonium.property.repository;

import br.com.patrimonium.property.entity.PropertyDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PropertyDocumentRepository
        extends JpaRepository<PropertyDocument, UUID> {

    List<PropertyDocument> findByPropertyId(UUID propertyId);
}