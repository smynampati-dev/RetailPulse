-- =====================================================
-- RETAILPULSE DOMAIN SQL QUERIES
-- =====================================================


-- =====================================================
-- 1. CRUD OPERATIONS (PRODUCT)
-- =====================================================

-- CREATE
INSERT INTO product (id, name, price, stock_quantity)
VALUES (101, 'Laptop', 80000, 10);

-- READ
SELECT * FROM product;

-- UPDATE
UPDATE product
SET price = 75000
WHERE id = 101;

-- DELETE
DELETE FROM product
WHERE id = 101;


-- =====================================================
-- 2. SEARCH QUERY (FILTER + PAGINATION + SORT)
-- =====================================================

SELECT *
FROM product
WHERE price >= 10000
  AND stock_quantity > 0
ORDER BY price DESC
    LIMIT 5 OFFSET 0;


-- =====================================================
-- 3. JOIN QUERY (ORDERS + USERS + PRODUCTS)
-- =====================================================

SELECT o.order_id,
       u.name AS user_name,
       p.name AS product_name,
       o.quantity
FROM orders o
         JOIN users u ON o.user_id = u.id
         JOIN product p ON o.product_id = p.id;


-- =====================================================
-- 4. STATISTIC QUERY (ORDERS PER PRODUCT)
-- =====================================================

SELECT p.name,
       COUNT(o.order_id) AS total_orders
FROM product p
         LEFT JOIN orders o ON p.id = o.product_id
GROUP BY p.name;


-- =====================================================
-- 5. TOP QUERY (MOST ORDERED PRODUCTS)
-- =====================================================

SELECT p.name,
       COUNT(o.order_id) AS total_orders
FROM product p
         JOIN orders o ON p.id = o.product_id
GROUP BY p.name
ORDER BY total_orders DESC
    LIMIT 5;


-- =====================================================
-- 6. BUSINESS LOGIC QUERY (PREVENT OVERSELLING)
-- =====================================================

-- Atomic update: reduce stock only if available
UPDATE product
SET stock_quantity = stock_quantity - 1
WHERE id = 101
  AND stock_quantity > 0;


-- =====================================================
-- 7. EXTRA QUERY (REVENUE PER PRODUCT)
-- =====================================================

SELECT p.name,
       SUM(o.quantity * p.price) AS revenue
FROM orders o
         JOIN product p ON o.product_id = p.id
GROUP BY p.name;
