CREATE TABLE properties (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,

    -- Identificação
    name VARCHAR(150) NOT NULL,
    description TEXT,

    -- Classificação
    type VARCHAR(50) NOT NULL,
    purpose VARCHAR(50) NOT NULL,
    category VARCHAR(50),

    -- Localização
    address VARCHAR(255),
    number VARCHAR(20),
    city VARCHAR(100),
    state VARCHAR(2),
    zip_code VARCHAR(20),

    -- Características
    bedrooms INTEGER,
    bathrooms INTEGER,
    suites INTEGER,
    parking_spots INTEGER,
    built_area NUMERIC(12,2),
    total_area NUMERIC(12,2),
    floor INTEGER,
    furnished BOOLEAN,

    -- Financeiro
    estimated_value NUMERIC(15,2),
    rent_value NUMERIC(15,2),
    condo_fee NUMERIC(12,2),
    iptu NUMERIC(12,2),

    -- Status
    active BOOLEAN DEFAULT TRUE,
    available_for_rent BOOLEAN DEFAULT FALSE,
    available_for_sale BOOLEAN DEFAULT FALSE,

    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,

    CONSTRAINT fk_property_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_property_user ON properties(user_id);
CREATE INDEX idx_property_city ON properties(city);
CREATE INDEX idx_property_state ON properties(state);
CREATE INDEX idx_property_type ON properties(type);