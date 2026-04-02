package com.retailpulse.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/retailpulse_db";
    private static final String USER = "retailpulse";
    private static final String PASSWORD = "retailpulse123";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {

        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

        // 🔥 TRANSACTION SETTINGS
        conn.setAutoCommit(false);

        // =====================================================
        // 🔵 DEMO MODE 1 → READ_COMMITTED (LESS STRICT)
        // ✔ Higher success rate
        // ✔ No errors
        // ❌ May allow race conditions
        // =====================================================
         conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);


        // =====================================================
        // 🔴 DEMO MODE 2 → SERIALIZABLE (STRICT)
        // ✔ Prevents overselling
        // ✔ Ensures full consistency
        // ❌ Some transactions fail (expected)
        // =====================================================
        //conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

        return conn;
    }
}
