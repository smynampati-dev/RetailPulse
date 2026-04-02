package com.retailpulse.repository;

import com.retailpulse.config.DBConnection;
import com.retailpulse.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbOrderRepository implements OrderRepository {

    @Override
    public void save(Order order) {
        String sql = "INSERT INTO orders (order_id, product_id, quantity, timestamp) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, order.getOrderId());
            stmt.setInt(2, order.getProductId());
            stmt.setInt(3, order.getQuantity());
            stmt.setTimestamp(4, Timestamp.valueOf(order.getTimestamp()));

            stmt.executeUpdate();
            conn.commit(); // ✅ IMPORTANT

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt("order_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getTimestamp("timestamp").toLocalDateTime()
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }
}
