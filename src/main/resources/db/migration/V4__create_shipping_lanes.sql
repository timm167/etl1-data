CREATE TABLE shipping_lanes (
        id BIGSERIAL PRIMARY KEY,
        origin_port_id BIGINT,
        destination_port_id BIGINT,
        origin_shipper_id BIGINT,
        destination_shipper_id BIGINT,

        CONSTRAINT fk_origin_port
            FOREIGN KEY (origin_port_id)
                REFERENCES locations (id)
                ON DELETE SET NULL,

        CONSTRAINT fk_destination_port
            FOREIGN KEY (destination_port_id)
                REFERENCES locations (id)
                ON DELETE SET NULL,

        CONSTRAINT fk_origin_shipper
            FOREIGN KEY (origin_shipper_id)
                REFERENCES shippers (id)
                ON DELETE SET NULL,

        CONSTRAINT fk_destination_shipper
            FOREIGN KEY (destination_shipper_id)
                REFERENCES shippers (id)
                ON DELETE SET NULL
);
