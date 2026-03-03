ALTER TABLE financial_transactions
ADD COLUMN payment_date DATE,
ADD COLUMN paid BOOLEAN DEFAULT FALSE;

UPDATE financial_transactions
SET paid = FALSE
WHERE paid IS NULL;

ALTER TABLE financial_transactions
ALTER COLUMN paid SET NOT NULL;