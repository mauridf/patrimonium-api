package br.com.patrimonium.contract.repository;

import br.com.patrimonium.contract.entity.ContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface ContractRepository
        extends JpaRepository<ContractEntity, UUID>,
        JpaSpecificationExecutor<ContractEntity> {
    Optional<ContractEntity> findByPropertyIdAndActiveTrue(UUID propertyId);
}
