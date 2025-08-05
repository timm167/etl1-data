CREATE TABLE cases (
    id SERIAL PRIMARY KEY,
    name TEXT,
    price DOUBLE PRECISION,
    type TEXT,
    color TEXT,
    side_panel TEXT,
    external_volume DOUBLE PRECISION,
    internal_35_bays INT
);