package com.retailpulse.rdbmsdemo;

import com.retailpulse.config.DBConnection;

import java.sql.Connection;
import java.sql.Statement;

public class RdbmsTransactionDemo {

    public static void main(String[] args) {

        withoutTransaction();
        withTransaction();
    }

    // ❌ WITHOUT TRANSACTION
    public static void withoutTransaction() {
        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();

            System.out.println("---- WITHOUT TRANSACTION ----");

            // Step 1: Reduce stock
            stmt.executeUpdate("UPDATE product SET stock_quantity = stock_quantity - 1 WHERE id = 1");

            // Step 2: Simulate failure
            if (true) throw new RuntimeException("Simulated Failure!");

            // Step 3: Insert order (never reached)
            stmt.executeUpdate("INSERT INTO orders(product_id, quantity) VALUES (1, 1)");

        } catch (Exception e) {
            System.out.println("❌ Error occurred: " + e.getMessage());
        }
    }

    // ✅ WITH TRANSACTION
    public static void withTransaction() {
        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();

            System.out.println("---- WITH TRANSACTION ----");

            conn.setAutoCommit(false);

            // Step 1: Reduce stock
            stmt.executeUpdate("UPDATE product SET stock_quantity = stock_quantity - 1 WHERE id = 1");

            // Step 2: Simulate failure
            if (true) throw new RuntimeException("Simulated Failure!");

            // Step 3: Insert order
            stmt.executeUpdate("INSERT INTO orders(product_id, quantity) VALUES (1, 1)");

            conn.commit();

        } catch (Exception e) {
            System.out.println("❌ Error occurred, rolling back...");
            try {
                Connection conn = DBConnection.getConnection();
                conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
