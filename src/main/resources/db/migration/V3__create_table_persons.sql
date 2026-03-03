CREATE TABLE persons (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,

    type VARCHAR(50) NOT NULL,
    full_name VARCHAR(150) NOT NULL,
    document VARCHAR(20) NOT NULL,
    email VARCHAR(150),
    phone VARCHAR(20),
    birth_date DATE,

    active BOOLEAN DEFAULT TRUE,

    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,

    CONSTRAINT fk_person_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_person_user ON persons(user_id);
CREATE INDEX idx_person_document ON persons(document);