package com.retailpulse.repository;

import com.retailpulse.jdbc.JdbcUtil;
import com.retailpulse.model.Product;

import java.util.List;

public class ProductDbRepository {

    // ================================
    // SAVE PRODUCT
    // ================================
    public void save(Product product) {
        JdbcUtil.execute(
                "INSERT INTO product(id, name, price, stock_quantity) VALUES (?, ?, ?, ?)",
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStockQuantity()
        );
    }

    // ================================
    // FIND BY ID
    // ================================
    public Product findById(int id) {
        return JdbcUtil.findOne(
                "SELECT * FROM product WHERE id = ?",
                rs -> {
                    try {
                        return new Product(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getDouble("price"),
                                rs.getInt("stock_quantity")
                        );
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                },
                id
        );
    }

    // ================================
    // FIND ALL
    // ================================
    public List<Product> findAll() {
        return JdbcUtil.findMany(
                "SELECT * FROM product",
                rs -> {
                    try {
                        return new Product(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getDouble("price"),
                                rs.getInt("stock_quantity")
                        );
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }

    // ================================
    // UPDATE STOCK
    // ================================
    public void updateStock(int id, int quantity) {
        JdbcUtil.execute(
                "UPDATE product SET stock_quantity = ? WHERE id = ?",
                quantity, id
        );
    }

    // ================================
    // DELETE
    // ================================
    public void delete(int id) {
        JdbcUtil.execute(
                "DELETE FROM product WHERE id = ?",
                id
        );
    }
}
