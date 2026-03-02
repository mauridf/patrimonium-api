package br.com.patrimonium.property.entity;

import br.com.patrimonium.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "properties")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyEntity {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity owner;

    // Identificação
    private String name;                 // Nome do imóvel

    // Classificação
    private String type;                 // RESIDENCIAL, COMERCIAL, TEMPORADA
    private String purpose;              // LOCACAO, VENDA, AMBOS
    private String category;             // CASA, APARTAMENTO, SALA, TERRENO, GALPÃO

    // Localização
    private String address;
    private String number;
    private String city;
    private String state;                // UF
    @Column(name = "zip_code")
    private String zipCode;

    // Características físicas
    private Integer bedrooms;            // Quartos
    private Integer bathrooms;           // Banheiros
    private Integer suites;              // Suítes
    @Column(name = "parking_spots")
    private Integer parkingSpots;        // Vagas garagem
    @Column(name = "total_area")
    private BigDecimal totalArea;        // Área total
    @Column(name = "built_area")
    private BigDecimal builtArea;        // Área construída
    private Integer floor;               // Andar (se aplicável)
    private Boolean furnished;           // Mobiliado
    @Column(columnDefinition = "TEXT")
    private String description;

    // Dados financeiros base
    @Column(name = "estimated_value")
    private BigDecimal estimatedValue;   // Valor estimado do imóvel
    @Column(name = "rent_value")
    private BigDecimal rentValue;        // Valor aluguel base
    @Column(name = "condo_fee")
    private BigDecimal condoFee;         // Condomínio
    private BigDecimal iptu;             // IPTU

    // Status
    private Boolean active;
    @Column(name = "available_for_rent")
    private Boolean availableForRent;
    @Column(name = "available_for_sale")
    private Boolean availableForSale;

    // Auditoria
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
        this.active = true;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}