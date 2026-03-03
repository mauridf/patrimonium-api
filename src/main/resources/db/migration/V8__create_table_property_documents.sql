CREATE TABLE property_documents (
    id UUID PRIMARY KEY,
    property_id UUID NOT NULL,
    document_type VARCHAR(50) NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    file_path VARCHAR(500) NOT NULL,
    content_type VARCHAR(100),
    created_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_document_property
        FOREIGN KEY (property_id)
        REFERENCES properties(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_document_property ON property_documents(property_id);
CREATE INDEX idx_document_type ON property_documents(document_type);