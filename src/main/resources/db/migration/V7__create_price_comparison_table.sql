CREATE TABLE price_comparison (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    price VARCHAR(255),
    origin VARCHAR(255),
    scrape_date TIMESTAMP,
    features TEXT
)