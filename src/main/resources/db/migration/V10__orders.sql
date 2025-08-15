CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    product_id INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    value NUMERIC(10, 2),
    distribution_channel_id INT NOT NULL,
    order_time TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    expected_delivery_time TIMESTAMP WITHOUT TIME ZONE,
    is_open BOOLEAN DEFAULT TRUE,
    address TEXT NOT NULL,

    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    CONSTRAINT fk_channel FOREIGN KEY (distribution_channel_id) REFERENCES distribution_channels(id) ON DELETE SET NULL
);