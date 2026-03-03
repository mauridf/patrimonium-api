CREATE TABLE property_valuations (
    id UUID PRIMARY KEY,
    property_id UUID NOT NULL,
    valuation_date DATE NOT NULL,
    amount NUMERIC(15,2) NOT NULL,
    notes TEXT,
    created_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_valuation_property
        FOREIGN KEY (property_id)
        REFERENCES properties(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_valuation_property ON property_valuations(property_id);
CREATE INDEX idx_valuation_date ON property_valuations(valuation_date);