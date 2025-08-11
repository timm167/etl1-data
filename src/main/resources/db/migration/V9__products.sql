DROP TABLE IF EXISTS products;

CREATE TABLE products (
      id SERIAL PRIMARY KEY,
      name TEXT NOT NULL,
      cost NUMERIC(10, 2),
      price NUMERIC(10, 2),
      color_id INT,
      case_id INT,
      cpu_id INT,
      cpu_cooler_id INT,
      graphics_card_id INT,
      internal_storage_id INT,
      memory_id INT,
      motherboard_id INT,
      power_supply_id INT,
      is_custom BOOLEAN DEFAULT FALSE,



      CONSTRAINT fk_color FOREIGN KEY (color_id) REFERENCES colors(id) ON DELETE SET NULL,
      CONSTRAINT fk_case FOREIGN KEY (case_id) REFERENCES cases(id) ON DELETE SET NULL,
      CONSTRAINT fk_cpu FOREIGN KEY (cpu_id) REFERENCES cpus(id) ON DELETE SET NULL,
      CONSTRAINT fk_cpu_cooler FOREIGN KEY (cpu_cooler_id) REFERENCES cpu_coolers(id) ON DELETE SET NULL,
      CONSTRAINT fk_gpu FOREIGN KEY (graphics_card_id) REFERENCES graphics_cards(id) ON DELETE SET NULL,
      CONSTRAINT fk_storage FOREIGN KEY (internal_storage_id) REFERENCES internal_storage(id) ON DELETE SET NULL,
      CONSTRAINT fk_memory FOREIGN KEY (memory_id) REFERENCES memory(id) ON DELETE SET NULL,
      CONSTRAINT fk_motherboard FOREIGN KEY (motherboard_id) REFERENCES motherboards(id) ON DELETE SET NULL,
      CONSTRAINT fk_psu FOREIGN KEY (power_supply_id) REFERENCES power_supplies(id) ON DELETE SET NULL
);