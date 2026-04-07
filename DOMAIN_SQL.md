# RetailPulse – Domain SQL Queries

## 1. Objective
Implement SQL queries based on real-world business logic of the RetailPulse inventory system.

---

## 2. CRUD Operations
Basic operations performed on the Product entity:
- Create new product
- Read product data
- Update product details
- Delete product

These operations simulate inventory management.

---

## 3. Search Queries
Implemented filtering, pagination, and sorting:
- Filter products by price and availability
- Sort results by price
- Limit results using pagination

This mimics real-world product listing APIs.

---

## 4. Join Queries
Combined data from multiple tables:
- Orders joined with Users and Products
- Enables displaying complete order details

This reflects how backend APIs fetch enriched data.

---

## 5. Statistical Queries
Calculated aggregate insights:
- Total number of orders per product

Used for analytics and reporting.

---

## 6. Top Queries
Identified:
- Most ordered products

Useful for business decisions and recommendations.

---

## 7. Business Logic Mapping
Implemented atomic stock update:

```sql
UPDATE product
SET stock_quantity = stock_quantity - 1
WHERE id = ?
AND stock_quantity > 0;
