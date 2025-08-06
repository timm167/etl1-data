DROP TABLE IF EXISTS shippers;

CREATE TABLE shippers (
      id SERIAL PRIMARY KEY,
      contact_phone VARCHAR(50),
      contact_url TEXT,
      facility_name VARCHAR(255) NOT NULL,
      formatted_address TEXT NOT NULL,
      lat DOUBLE PRECISION,
      lon DOUBLE PRECISION,
      operating_hours TEXT,
      place_category VARCHAR(100)
);