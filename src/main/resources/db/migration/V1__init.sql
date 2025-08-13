CREATE TYPE location_type_enum AS ENUM (
  'DISTRIBUTION_FACILITY',
  'WAREHOUSE',
  'FACTORY',
  'OFFICE'
);

CREATE TABLE locations (
       id SERIAL PRIMARY KEY,
       location_type location_type_enum NOT NULL,
       location_name VARCHAR(100) NOT NULL,
       country VARCHAR(100) NOT NULL,
       lat NUMERIC(9,6),
       lon NUMERIC(9,6)
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


INSERT INTO locations (location_type, location_name, country, lat, lon) VALUES
    ('OFFICE', 'Head Office London', 'United Kingdom', 51.507351, -0.127758),

    ('DISTRIBUTION_FACILITY', 'Distribution Facility London East', 'United Kingdom', 51.515, -0.070),
    ('DISTRIBUTION_FACILITY', 'Distribution Facility Manchester', 'United Kingdom', 53.480, -2.240),
    ('DISTRIBUTION_FACILITY', 'Distribution Facility Birmingham', 'United Kingdom', 52.4862, -1.8904),
    ('DISTRIBUTION_FACILITY', 'Distribution Facility Glasgow', 'United Kingdom', 55.8642, -4.2518),
    ('DISTRIBUTION_FACILITY', 'Distribution Facility Newcastle', 'United Kingdom', 54.9784, -1.6174),

    ('WAREHOUSE', 'Warehouse London East', 'United Kingdom', 51.517, -0.072),
    ('WAREHOUSE', 'Warehouse Manchester', 'United Kingdom', 53.482, -2.235),
    ('WAREHOUSE', 'Warehouse Birmingham', 'United Kingdom', 52.4862, -1.8904),
    ('WAREHOUSE', 'Warehouse Glasgow', 'United Kingdom', 55.8609, -4.2514),
    ('WAREHOUSE', 'Warehouse Newcastle', 'United Kingdom', 54.9783, -1.6174),

    ('FACTORY', 'Factory Birmingham', 'United Kingdom', 52.480, -1.900),
    ('FACTORY', 'Factory Sheffield', 'United Kingdom', 53.3811, -1.4701),
    ('FACTORY', 'Factory Liverpool', 'United Kingdom', 53.4084, -2.9916),
    ('FACTORY', 'Factory Cardiff', 'United Kingdom', 51.4816, -3.1791),
    ('FACTORY', 'Factory Belfast', 'United Kingdom', 54.5973, -5.9301);


-- SAMPLE DATA --
INSERT INTO staff (first_name, last_name, email, job_title, location_id, salary) VALUES
     ('John', 'Smith', 'john.smith@company.com', 'Manager', 1, 75000.00),
     ('Sarah', 'Johnson', 'sarah.johnson@company.com', 'Developer', 2, 65000.00),
     ('Mike', 'Chen', 'mike.chen@company.com', 'Analyst', 3, 60000.00),
     ('Lisa', 'Wilson', 'lisa.wilson@company.com', 'Designer', 1, 55000.00),
     ('David', 'Brown', 'david.brown@company.com', 'Sales Rep', 2, 50000.00),
     ('Emily', 'Davis', 'emily.davis@company.com', 'Developer', 3, 67000.00),
     ('James', 'Miller', 'james.miller@company.com', 'Manager', 4, 78000.00),
     ('Anna', 'Garcia', 'anna.garcia@company.com', 'Designer', 5, 54000.00),
     ('Robert', 'Martinez', 'robert.martinez@company.com', 'Sales Rep', 6, 51000.00),
     ('Laura', 'Lopez', 'laura.lopez@company.com', 'Developer', 7, 66000.00),
     ('Mark', 'Gonzalez', 'mark.gonzalez@company.com', 'Analyst', 1, 62000.00),
     ('Sophie', 'Perez', 'sophie.perez@company.com', 'Manager', 2, 77000.00),
     ('Paul', 'Wilson', 'paul.wilson@company.com', 'Designer', 3, 53000.00),
     ('Nina', 'Clark', 'nina.clark@company.com', 'Developer', 4, 64000.00),
     ('Tom', 'Lewis', 'tom.lewis@company.com', 'Sales Rep', 5, 48000.00),
     ('Megan', 'Young', 'megan.young@company.com', 'Analyst', 6, 61000.00),
     ('Chris', 'Hall', 'chris.hall@company.com', 'Developer', 7, 67000.00),
     ('Jessica', 'Allen', 'jessica.allen@company.com', 'Manager', 1, 80000.00),
     ('Kevin', 'King', 'kevin.king@company.com', 'Sales Rep', 2, 49000.00),
     ('Laura', 'Wright', 'laura.wright@company.com', 'Designer', 3, 55000.00),
     ('Ryan', 'Scott', 'ryan.scott@company.com', 'Developer', 4, 63000.00),
     ('Olivia', 'Green', 'olivia.green@company.com', 'Analyst', 5, 60000.00),
     ('Jacob', 'Adams', 'jacob.adams@company.com', 'Manager', 6, 76000.00),
     ('Sophia', 'Baker', 'sophia.baker@company.com', 'Developer', 7, 67000.00),
     ('Ethan', 'Nelson', 'ethan.nelson@company.com', 'Sales Rep', 1, 51000.00),
     ('Grace', 'Hill', 'grace.hill@company.com', 'Designer', 2, 54000.00),
     ('Jack', 'Campbell', 'jack.campbell@company.com', 'Analyst', 3, 61000.00),
     ('Chloe', 'Mitchell', 'chloe.mitchell@company.com', 'Developer', 4, 65000.00),
     ('Luke', 'Roberts', 'luke.roberts@company.com', 'Manager', 5, 78000.00),
     ('Ella', 'Carter', 'ella.carter@company.com', 'Sales Rep', 6, 50000.00),
     ('Adam', 'Phillips', 'adam.phillips@company.com', 'Designer', 7, 56000.00),
     ('Mia', 'Evans', 'mia.evans@company.com', 'Developer', 1, 66000.00),
     ('Noah', 'Turner', 'noah.turner@company.com', 'Analyst', 2, 62000.00),
     ('Zoe', 'Parker', 'zoe.parker@company.com', 'Manager', 3, 79000.00),
     ('Liam', 'Collins', 'liam.collins@company.com', 'Developer', 4, 67000.00),
     ('Isabella', 'Edwards', 'isabella.edwards@company.com', 'Designer', 5, 55000.00),
     ('Benjamin', 'Stewart', 'benjamin.stewart@company.com', 'Sales Rep', 6, 49000.00),
     ('Hannah', 'Morris', 'hannah.morris@company.com', 'Developer', 7, 64000.00),
     ('Oliver', 'Rogers', 'oliver.rogers@company.com', 'Analyst', 1, 62000.00),
     ('Amelia', 'Reed', 'amelia.reed@company.com', 'Manager', 2, 78000.00),
     ('Jacob', 'Cook', 'jacob.cook@company.com', 'Designer', 3, 53000.00),
     ('Emily', 'Morgan', 'emily.morgan@company.com', 'Developer', 4, 66000.00),
     ('Michael', 'Bell', 'michael.bell@company.com', 'Sales Rep', 5, 50000.00),
     ('Charlotte', 'Murphy', 'charlotte.murphy@company.com', 'Analyst', 6, 60000.00),
     ('David', 'Bailey', 'david.bailey@company.com', 'Developer', 7, 67000.00),
     ('Samantha', 'Cooper', 'samantha.cooper@company.com', 'Manager', 1, 79000.00),
     ('James', 'Richardson', 'james.richardson@company.com', 'Sales Rep', 2, 48000.00);
