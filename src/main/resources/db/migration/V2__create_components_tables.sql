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

CREATE TABLE cpus (
    id SERIAL PRIMARY KEY,
    name TEXT,
    price DOUBLE PRECISION,
    core_count INT,
    core_clock DOUBLE PRECISION,
    boost_clock DOUBLE PRECISION,
    microarchitecture TEXT,
    tdp INT,
    graphics TEXT
);

CREATE TABLE cpu_coolers (
    id SERIAL PRIMARY KEY,
    name TEXT,
    price DOUBLE PRECISION,
    color TEXT,
    size INT
);

CREATE TABLE fan_rpm (
    id SERIAL PRIMARY KEY,
    rpm INT,
    cpu_cooler_id INT,
    constraint fk_cpu_cooler FOREIGN KEY(cpu_cooler_id) REFERENCES cpu_coolers(id) ON DELETE CASCADE
);

CREATE TABLE fan_noise_level (
    id SERIAL PRIMARY KEY,
    noise_level DOUBLE PRECISION,
    cpu_cooler_id INT,
    constraint fk_cpu_cooler FOREIGN KEY(cpu_cooler_id) REFERENCES cpu_coolers(id) ON DELETE CASCADE
);

CREATE TABLE graphics_cards (
    id SERIAL PRIMARY KEY,
    name TEXT,
    price DOUBLE PRECISION,
    chipset TEXT,
    memory INT,
    core_clock INT,
    boost_clock INT,
    color TEXT,
    length INT
);

CREATE TABLE internal_storage (
    id SERIAL PRIMARY KEY,
    name TEXT,
    price DOUBLE PRECISION,
    capacity INT,
    price_per_gb DOUBLE PRECISION,
    type TEXT,
    cache INT,
    form_factor TEXT
);

CREATE TABLE memory (
    id SERIAL PRIMARY KEY,
    name TEXT,
    price DOUBLE PRECISION,
    price_per_gb DOUBLE PRECISION,
    color TEXT,
    first_word_latency INT,
    cas_latency INT
);

CREATE TABLE memory_speed (
    id SERIAL PRIMARY KEY,
    speed INT,
    memory_id INT,
    constraint fk_memory FOREIGN KEY (memory_id) REFERENCES memory(id) ON DELETE CASCADE
);

CREATE TABLE memory_modules (
    id SERIAL PRIMARY KEY,
    module INT,
    memory_id INT,
    constraint fk_memory FOREIGN KEY (memory_id) REFERENCES memory(id) ON DELETE CASCADE
);

CREATE TABLE motherboards (
    id SERIAL PRIMARY KEY ,
    name TEXT,
    price DOUBLE PRECISION,
    socket TEXT,
    form_factor TEXT,
    max_memory INT,
    memory_slots INT,
    color TEXT
);

CREATE TABLE power_supplies (
    id SERIAL PRIMARY KEY,
    name TEXT,
    price DOUBLE PRECISION,
    type TEXT,
    efficiency TEXT,
    wattage INT,
    modular TEXT,
    color TEXT
);