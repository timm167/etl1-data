CREATE TABLE basket_items (
    id BIGSERIAL PRIMARY KEY,
     basket_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INTEGER NOT NULL,
    FOREIGN KEY (basket_id) REFERENCES baskets(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id)
);