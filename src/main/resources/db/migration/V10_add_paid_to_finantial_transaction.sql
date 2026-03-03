ALTER TABLE financial_transactions
ADD COLUMN payment_date DATE,
ADD COLUMN paid BOOLEAN DEFAULT FALSE;