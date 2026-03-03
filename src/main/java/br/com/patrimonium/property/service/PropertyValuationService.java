package br.com.patrimonium.property.service;

import br.com.patrimonium.property.entity.PropertyValuation;
import br.com.patrimonium.property.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PropertyValuationService {

    private final PropertyRepository propertyRepository;
    private final PropertyValuationRepository repository;

    public void add(UUID propertyId, BigDecimal amount, LocalDate date, String notes) {

        var property = propertyRepository.findById(propertyId)
                .orElseThrow();

        var valuation = PropertyValuation.builder()
                .property(property)
                .valuationDate(date)
                .amount(amount)
                .notes(notes)
                .build();

        repository.save(valuation);

        property.setEstimatedValue(amount);
        propertyRepository.save(property);
    }

    public List<PropertyValuation> history(UUID propertyId) {
        return repository.findByPropertyIdOrderByValuationDateAsc(propertyId);
    }
}
