CREATE TABLE distribution_channels (
       id SERIAL PRIMARY KEY,
       name VARCHAR(100) NOT NULL UNIQUE,
       warehouse_id INTEGER REFERENCES locations(id) ON DELETE SET NULL,
       distribution_facility_id INTEGER REFERENCES locations(id) ON DELETE SET NULL,
       shipping_lane_id INTEGER REFERENCES shipping_lanes(id) ON DELETE SET NULL,
       active BOOLEAN DEFAULT TRUE,
       created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
       updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
