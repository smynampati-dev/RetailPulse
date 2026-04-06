package com.retailpulse.jdbc;

import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcUtilTest {

    private Connection getConnection() throws Exception {
        return DriverManager.getConnection("jdbc:h2:mem:testdb");
    }

    @Test
    void testExecuteAndFindOne() throws Exception {

        Connection conn = getConnection();
        Statement stmt = conn.createStatement();

        stmt.execute("CREATE TABLE product(id INT, name VARCHAR(100))");

        stmt.execute("INSERT INTO product VALUES (1, 'Phone')");

        JdbcUtil.execute(
                "INSERT INTO product VALUES (?, ?)",
                2, "Laptop"
        );

        String name = JdbcUtil.findOne(
                "SELECT name FROM product WHERE id = ?",
                rs -> {
                    try {
                        return rs.getString("name");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                },
                2
        );

        assertEquals("Laptop", name);

        conn.close();
    }

    @Test
    void testFindMany() {

        List<String> products = JdbcUtil.findMany(
                "SELECT name FROM product",
                rs -> {
                    try {
                        return rs.getString("name");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        assertNotNull(products);
    }
}
