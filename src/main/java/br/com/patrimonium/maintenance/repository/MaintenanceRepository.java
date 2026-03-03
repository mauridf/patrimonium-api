package br.com.patrimonium.maintenance.repository;

import br.com.patrimonium.maintenance.entity.MaintenanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MaintenanceRepository
        extends JpaRepository<MaintenanceEntity, UUID> {

    List<MaintenanceEntity> findByPropertyId(UUID propertyId);
}
