package br.com.patrimonium.contract.service;

import br.com.patrimonium.contract.dto.*;
import br.com.patrimonium.contract.entity.ContractEntity;
import br.com.patrimonium.contract.enums.ContractStatus;
import br.com.patrimonium.contract.repository.ContractRepository;
import br.com.patrimonium.person.entity.PersonEntity;
import br.com.patrimonium.person.repository.PersonRepository;
import br.com.patrimonium.property.repository.PropertyRepository;
import br.com.patrimonium.user.entity.UserEntity;
import br.com.patrimonium.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository repository;
    private final PropertyRepository propertyRepository;
    private final PersonRepository personRepository;
    private final UserRepository userRepository;

    private UserEntity getAuthenticatedUser() {
        var email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow();
    }

    public ContractResponse create(ContractCreateRequest request) {

        var user = getAuthenticatedUser();

        var property = propertyRepository.findById(request.propertyId())
                .orElseThrow();

        var landlord = personRepository.findById(request.landlordId()).orElseThrow();
        var tenant = personRepository.findById(request.tenantId()).orElseThrow();

        PersonEntity guarantor = null;
        if (request.guarantorId() != null) {
            guarantor = personRepository.findById(request.guarantorId()).orElseThrow();
        }

        var contract = ContractEntity.builder()
                .id(UUID.randomUUID())
                .user(user)
                .property(property)
                .landlord(landlord)
                .tenant(tenant)
                .guarantor(guarantor)
                .type(request.type())
                .status(ContractStatus.ACTIVE)
                .contractValue(request.contractValue())
                .monthlyValue(request.monthlyValue())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .finePercentage(request.finePercentage())
                .interestPercentage(request.interestPercentage())
                .active(true)
                .createdAt(LocalDateTime.now())
                .build();

        repository.save(contract);

        return toResponse(contract);
    }

    private ContractResponse toResponse(ContractEntity c) {
        return new ContractResponse(
                c.getId(),
                c.getProperty().getId(),
                c.getLandlord().getId(),
                c.getTenant().getId(),
                c.getGuarantor() != null ? c.getGuarantor().getId() : null,
                c.getType(),
                c.getStatus(),
                c.getContractValue(),
                c.getMonthlyValue(),
                c.getStartDate(),
                c.getEndDate(),
                c.getActive()
        );
    }
}
