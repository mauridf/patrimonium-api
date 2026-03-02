package br.com.patrimonium.property.entity;

import br.com.patrimonium.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

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
    private String name;
    private String type;     // RESIDENCIAL, COMERCIAL, TEMPORADA
    private String purpose;  // LOCACAO, VENDA, AMBOS

    // Localização
    private String address;
    private String city;
    private String state;
    private String zipCode;

    // Características físicas
    private Integer bedrooms;
    private Integer bathrooms;
    private Integer garageSpots;
    private Double areaTotal;
    private Double areaBuilt;
    private Boolean furnished;

    // Financeiro base
    private Double valueSale;
    private Double valueRent;

    // Extra
    private String description;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
        if (this.furnished == null) this.furnished = false;
    }
}