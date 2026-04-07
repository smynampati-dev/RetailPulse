-- CREATE TABLE
CREATE TABLE product (
                         id INT PRIMARY KEY,
                         name VARCHAR(100),
                         price DOUBLE,
                         stock_quantity INT
);

-- INSERT DATA
INSERT INTO product VALUES (1, 'Phone', 50000, 10);
INSERT INTO product VALUES (2, 'Laptop', 80000, 5);
