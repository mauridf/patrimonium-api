CREATE TABLE property_images (
    id UUID PRIMARY KEY,
    property_id UUID NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    file_path VARCHAR(500) NOT NULL,
    content_type VARCHAR(100),
    created_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_image_property
        FOREIGN KEY (property_id)
        REFERENCES properties(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_image_property ON property_images(property_id);