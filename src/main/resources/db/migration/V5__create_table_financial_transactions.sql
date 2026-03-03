CREATE TABLE financial_transactions (
    id UUID PRIMARY KEY,
    property_id UUID NOT NULL,
    amount NUMERIC(15,2) NOT NULL,
    type VARCHAR(20) NOT NULL,
    transaction_date DATE NOT NULL,
    description TEXT,
    created_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_transaction_property
        FOREIGN KEY (property_id)
        REFERENCES properties(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_transaction_property ON financial_transactions(property_id);
CREATE INDEX idx_transaction_date ON financial_transactions(transaction_date);
CREATE INDEX idx_transaction_type ON financial_transactions(type);