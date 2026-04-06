-- ===============================
-- RETAILPULSE DATABASE SCHEMA
-- ===============================

-- USERS TABLE
CREATE TABLE IF NOT EXISTS users (
                                     id SERIAL PRIMARY KEY,
                                     name VARCHAR(100) NOT NULL
    );

-- PRODUCT TABLE
CREATE TABLE IF NOT EXISTS product (
                                       id INT PRIMARY KEY,
                                       name VARCHAR(100) UNIQUE NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    stock_quantity INT NOT NULL,
    product_code VARCHAR(20)
    );

-- ORDERS TABLE
CREATE TABLE IF NOT EXISTS orders (
                                      order_id INT PRIMARY KEY,
                                      user_id INT,
                                      product_id INT,
                                      quantity INT NOT NULL,
                                      timestamp TIMESTAMP,

                                      CONSTRAINT fk_user
                                      FOREIGN KEY (user_id)
    REFERENCES users(id),

    CONSTRAINT fk_product
    FOREIGN KEY (product_id)
    REFERENCES product(id)
    );

-- ONE-TO-ONE TABLE (USER PROFILE)
CREATE TABLE IF NOT EXISTS user_profile (
                                            user_id INT PRIMARY KEY,
                                            email VARCHAR(100),
    address VARCHAR(200),

    CONSTRAINT fk_user_profile
    FOREIGN KEY (user_id)
    REFERENCES users(id)
    );

-- ===============================
-- INDEXES (OPTIONAL OPTIMIZATION)
-- ===============================

CREATE INDEX IF NOT EXISTS idx_product_name ON product(name);
CREATE INDEX IF NOT EXISTS idx_orders_user ON orders(user_id);
CREATE INDEX IF NOT EXISTS idx_orders_product ON orders(product_id);