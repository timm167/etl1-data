
CREATE TABLE colors (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    hex_code VARCHAR(7) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,

    CONSTRAINT uk_colors_name UNIQUE (name),
    CONSTRAINT uk_colors_hex_code UNIQUE (hex_code),


    CONSTRAINT chk_hex_code_format CHECK (hex_code ~ '^#[0-9A-Fa-f]{6}$')
);

CREATE INDEX idx_colors_name ON colors(name);
CREATE INDEX idx_colors_hex_code ON colors(hex_code);
CREATE INDEX idx_colors_created_at ON colors(created_at);


INSERT INTO colors (name, hex_code) VALUES
    ('Pure White', '#FFFFFF'),
    ('Jet Black', '#000000'),
    ('Classic Red', '#FF0000'),
    ('Ocean Blue', '#0066CC'),
    ('Forest Green', '#228B22')
ON CONFLICT (hex_code) DO NOTHING;