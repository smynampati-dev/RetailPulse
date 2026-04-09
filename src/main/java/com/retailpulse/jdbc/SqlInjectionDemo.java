package com.retailpulse.jdbc;

import java.sql.*;

public class SqlInjectionDemo {

    private static final String URL = "jdbc:postgresql://localhost:5432/retailpulse_db";
    private static final String USER = "retailpulse";
    private static final String PASSWORD = "retailpulse123";

    public static void main(String[] args) throws Exception {

        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

        String userInput = "' OR '1'='1";

        System.out.println("=== Using Statement (VULNERABLE) ===");
        statementExample(conn, userInput);

        System.out.println("\n=== Using PreparedStatement (SAFE) ===");
        preparedStatementExample(conn, userInput);

        conn.close();
    }

    // ❌ Vulnerable
    private static void statementExample(Connection conn, String input) throws Exception {

        Statement stmt = conn.createStatement();

        String query = "SELECT * FROM users WHERE name = '" + input + "'";

        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            System.out.println("User: " + rs.getString("name"));
        }

        stmt.close();
    }

    // ✅ Safe
    private static void preparedStatementExample(Connection conn, String input) throws Exception {

        PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM users WHERE name = ?"
        );

        ps.setString(1, input);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            System.out.println("User: " + rs.getString("name"));
        }

        ps.close();
    }
}
