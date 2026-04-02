package com.retailpulse.repository;

import com.retailpulse.config.DBConnection;
import com.retailpulse.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbProductRepository implements ProductRepository {

    @Override
    public boolean save(Product product) {

        String sql = "INSERT INTO product (id, name, price, stock_quantity) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, product.getId());
            stmt.setString(2, product.getName());
            stmt.setDouble(3, product.getPrice());
            stmt.setInt(4, product.getStockQuantity());

            stmt.executeUpdate();
            conn.commit();

            // ❌ REMOVED SUCCESS PRINT (important fix)
            return true;

        } catch (SQLException e) {

            if (e.getMessage().contains("unique_product_name")) {
                System.out.println("❌ Product already exists!");
            } else {
                e.printStackTrace();
            }

            return false;
        }
    }

    @Override
    public Product findById(int id) {

        String sql = "SELECT * FROM product WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock_quantity")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Product> findAll() {

        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM product";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock_quantity")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public void delete(int id) {

        String sql = "DELETE FROM product WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔥 ATOMIC METHOD (FLASH SALE CORE)
    public boolean decreaseStockAtomic(int productId, int quantity) {

        String sql = """
            UPDATE product
            SET stock_quantity = stock_quantity - ?
            WHERE id = ? AND stock_quantity >= ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, quantity);
            stmt.setInt(2, productId);
            stmt.setInt(3, quantity);

            int rows = stmt.executeUpdate();

            conn.commit();

            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
