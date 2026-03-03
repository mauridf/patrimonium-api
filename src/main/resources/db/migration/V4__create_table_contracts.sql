CREATE TABLE contracts (
    id UUID PRIMARY KEY,

    user_id UUID NOT NULL,
    property_id UUID NOT NULL,
    landlord_id UUID NOT NULL,
    tenant_id UUID NOT NULL,
    guarantor_id UUID,

    type VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,

    contract_value NUMERIC(15,2),
    monthly_value NUMERIC(15,2),

    start_date DATE NOT NULL,
    end_date DATE,

    fine_percentage NUMERIC(5,2),
    interest_percentage NUMERIC(5,2),

    active BOOLEAN DEFAULT TRUE,

    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,

    CONSTRAINT fk_contract_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_contract_property
        FOREIGN KEY (property_id)
        REFERENCES properties(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_contract_landlord
        FOREIGN KEY (landlord_id)
        REFERENCES persons(id),

    CONSTRAINT fk_contract_tenant
        FOREIGN KEY (tenant_id)
        REFERENCES persons(id),

    CONSTRAINT fk_contract_guarantor
        FOREIGN KEY (guarantor_id)
        REFERENCES persons(id)
);

CREATE INDEX idx_contract_user ON contracts(user_id);
CREATE INDEX idx_contract_property ON contracts(property_id);
CREATE INDEX idx_contract_status ON contracts(status);