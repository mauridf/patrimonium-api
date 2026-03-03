package br.com.patrimonium.person.repository;

import br.com.patrimonium.person.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface PersonRepository extends
        JpaRepository<PersonEntity, UUID>,
        JpaSpecificationExecutor<PersonEntity> {
}