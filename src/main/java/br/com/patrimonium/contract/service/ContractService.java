package br.com.patrimonium.contract.service;

import br.com.patrimonium.contract.dto.*;
import br.com.patrimonium.contract.entity.ContractEntity;
import br.com.patrimonium.contract.enums.ContractStatus;
import br.com.patrimonium.contract.enums.ContractType;
import br.com.patrimonium.contract.repository.ContractRepository;
import br.com.patrimonium.notification.service.NotificationService;
import br.com.patrimonium.person.entity.PersonEntity;
import br.com.patrimonium.person.repository.PersonRepository;
import br.com.patrimonium.property.repository.PropertyRepository;
import br.com.patrimonium.transaction.entity.FinancialTransaction;
import br.com.patrimonium.transaction.enums.TransactionType;
import br.com.patrimonium.transaction.repository.FinancialTransactionRepository;
import br.com.patrimonium.user.entity.UserEntity;
import br.com.patrimonium.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository repository;
    private final FinancialTransactionRepository transactionRepository;
    private final PropertyRepository propertyRepository;
    private final PersonRepository personRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

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

        if (request.dueDay() < 1 || request.dueDay() > 31) {
            throw new IllegalArgumentException("Invalid due day");
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

        if (contract.getType() == ContractType.RENTAL) {

            LocalDate current = contract.getStartDate();

            while (!current.isAfter(contract.getEndDate())) {

                FinancialTransaction tx = FinancialTransaction.builder()
                        .property(contract.getProperty())
                        .amount(contract.getMonthlyValue())
                        .type(TransactionType.INCOME)
                        .transactionDate(current)
                        .description("Rent - Contract " + contract.getId())
                        .build();

                transactionRepository.save(tx);

                current = current.plusMonths(1);
            }
        }

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
                c.getDueDay(),
                c.getStartDate(),
                c.getEndDate(),
                c.getActive()
        );
    }

    @Transactional
    public void checkDefault() {

        List<FinancialTransaction> overdue =
                transactionRepository.findOverdueTransactions(LocalDate.now());

        for (FinancialTransaction tx : overdue) {

            Optional<ContractEntity> contract =
                    repository.findByPropertyIdAndActiveTrue(
                            tx.getProperty().getId()
                    );

            contract.ifPresent(c -> {

                c.setStatus(ContractStatus.DEFAULTED);
                repository.save(c);

                notificationService.sendDefaultNotification(c);
            });
        }
    }
}
