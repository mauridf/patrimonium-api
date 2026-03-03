CREATE TABLE maintenances (
    id UUID PRIMARY KEY,
    property_id UUID NOT NULL,
    title VARCHAR(255),
    description TEXT,
    cost NUMERIC(15,2),
    status VARCHAR(30),
    open_date DATE,
    completion_date DATE,
    created_at TIMESTAMP,

    CONSTRAINT fk_maintenance_property
        FOREIGN KEY (property_id)
        REFERENCES properties(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_maintenance_property ON maintenances(property_id);
CREATE INDEX idx_maintenance_status ON maintenances(status);