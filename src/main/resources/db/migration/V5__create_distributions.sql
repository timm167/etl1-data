CREATE TABLE distribution_channels (
       id SERIAL PRIMARY KEY,
       name VARCHAR(100) NOT NULL UNIQUE,
       warehouse_id INTEGER,
       distribution_facility_id INTEGER,
       shipping_lane_id INTEGER,
       active BOOLEAN DEFAULT TRUE,
       created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
       updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

       CONSTRAINT fk_warehouse FOREIGN KEY (warehouse_id) REFERENCES locations(id) ON DELETE SET NULL,
       CONSTRAINT fk_distribution_facility FOREIGN KEY (distribution_facility_id) REFERENCES locations(id) ON DELETE SET NULL,
       CONSTRAINT fk_shipping_lane FOREIGN KEY (shipping_lane_id) REFERENCES shipping_lanes(id) ON DELETE SET NULL
);

