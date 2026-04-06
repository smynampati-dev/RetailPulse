-- ================================
-- CONTINENTS
-- ================================
CREATE TABLE continents (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(100) UNIQUE NOT NULL
);

-- ================================
-- COUNTRIES
-- ================================
CREATE TABLE countries (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(100) NOT NULL,
                           area DOUBLE PRECISION,
                           population BIGINT,
                           continent_id INT,
                           FOREIGN KEY (continent_id) REFERENCES continents(id)
);

-- ================================
-- PEOPLE
-- ================================
CREATE TABLE people (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(100) NOT NULL
);

-- ================================
-- MANY-TO-MANY (CITIZENSHIP)
-- ================================
CREATE TABLE citizenship (
                             person_id INT,
                             country_id INT,
                             PRIMARY KEY (person_id, country_id),
                             FOREIGN KEY (person_id) REFERENCES people(id),
                             FOREIGN KEY (country_id) REFERENCES countries(id)
);

-- ================================
-- INSERT CONTINENTS
-- ================================
INSERT INTO continents (name) VALUES
                                  ('Asia'),
                                  ('Europe'),
                                  ('Africa'),
                                  ('North America'),
                                  ('South America');

-- ================================
-- INSERT COUNTRIES
-- ================================
INSERT INTO countries (name, area, population, continent_id) VALUES
                                                                 ('India', 3287263, 1400000000, 1),
                                                                 ('China', 9596961, 1410000000, 1),
                                                                 ('Germany', 357022, 83000000, 2),
                                                                 ('France', 551695, 67000000, 2),
                                                                 ('Nigeria', 923768, 223000000, 3),
                                                                 ('Egypt', 1002450, 110000000, 3),
                                                                 ('USA', 9833517, 331000000, 4),
                                                                 ('Canada', 9984670, 38000000, 4),
                                                                 ('Brazil', 8515767, 214000000, 5),
                                                                 ('Argentina', 2780400, 45000000, 5);

-- ================================
-- INSERT PEOPLE
-- ================================
INSERT INTO people (name) VALUES
                              ('John'),
                              ('Alice'),
                              ('Bob'),
                              ('Charlie'),
                              ('David'),
                              ('Emma'),
                              ('John');

-- ================================
-- INSERT CITIZENSHIP
-- ================================
INSERT INTO citizenship (person_id, country_id) VALUES
                                                    (1, 7),  -- John → USA
                                                    (1, 8),  -- John → Canada
                                                    (2, 3),  -- Alice → Germany
                                                    (3, 1),  -- Bob → India
                                                    (4, 2),  -- Charlie → China
                                                    (5, 5),  -- David → Nigeria
                                                    (6, 4),  -- Emma → France;
