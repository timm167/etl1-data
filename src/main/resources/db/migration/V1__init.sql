CREATE TYPE location_type_enum AS ENUM (
    'distribution facility',
    'warehouse',
    'factory',
    'office'
);

CREATE TABLE locations (
       id SERIAL PRIMARY KEY,
       location_type location_type_enum NOT NULL,
       location_name VARCHAR(100) NOT NULL,
       country VARCHAR(100) NOT NULL
);

CREATE TABLE staff (
       id SERIAL PRIMARY KEY,
       first_name VARCHAR(50) NOT NULL,
       last_name VARCHAR(50) NOT NULL,
       email VARCHAR(100) NOT NULL,
       job_title VARCHAR(100) NOT NULL,
       location_id INT NOT NULL,
       salary DECIMAL(10,2),

       CONSTRAINT fk_staff_location
           FOREIGN KEY (location_id)
               REFERENCES locations(id)
);


-- SAMPLE LOCATIONS --
INSERT INTO locations (location_type, location_name, country) VALUES
('office', 'Head Office - Newcastle', 'UK'),
('office', 'London Branch', 'UK'),
('office', 'São Paulo Office', 'Brazil'),
('warehouse', 'Manchester Warehouse', 'UK'),
('factory', 'Berlin Plant', 'Germany'),
('distribution facility', 'Madrid Distribution Center', 'Spain');

-- SAMPLE DATA --
INSERT INTO staff (first_name, last_name, email, job_title, location_id, salary) VALUES
('John', 'Smith', 'john.smith@company.com', 'Manager', 1, 75000.00),
('Sarah', 'Johnson', 'sarah.johnson@company.com', 'Developer', 2, 65000.00),
('Mike', 'Chen', 'mike.chen@company.com', 'Analyst', 3, 60000.00),
('Lisa', 'Wilson', 'lisa.wilson@company.com', 'Designer', 1, 55000.00),
('David', 'Brown', 'david.brown@company.com', 'Sales Rep', 2, 50000.00);
