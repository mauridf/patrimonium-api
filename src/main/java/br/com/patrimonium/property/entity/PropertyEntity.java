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

    private String name;
    private String type;     // RESIDENCIAL, COMERCIAL, TEMPORADA
    private String purpose;  // LOCACAO, VENDA, AMBOS
    private String address;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
    }
}