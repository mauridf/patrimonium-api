ALTER TABLE financial_transactions
ADD COLUMN reference_transaction_id UUID;

ALTER TABLE financial_transactions
ADD CONSTRAINT fk_financial_transaction_reference
FOREIGN KEY (reference_transaction_id)
REFERENCES financial_transactions(id);

ALTER TABLE financial_transactions
ADD COLUMN status VARCHAR(30) NOT NULL DEFAULT 'PENDING';