CREATE TABLE product_reviews (
  id SERIAL PRIMARY KEY,
  product_id INT NOT NULL,
  name TEXT NOT NULL DEFAULT '',
  email TEXT NOT NULL,
  content TEXT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT uk_product_review_user_email UNIQUE (product_id, email),
  CONSTRAINT fk_product_review_product_id FOREIGN KEY (product_id)
    REFERENCES products(id) ON DELETE CASCADE
);

CREATE INDEX idx_reviews_product ON product_reviews(product_id);