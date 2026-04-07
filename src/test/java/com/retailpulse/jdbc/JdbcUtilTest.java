package com.retailpulse.jdbc;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcUtilTest {

    @BeforeAll
    static void setup() throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb");
        Statement stmt = conn.createStatement();

        stmt.execute("CREATE TABLE product(id INT PRIMARY KEY, name VARCHAR(100))");
        stmt.execute("INSERT INTO product VALUES (1, 'Phone')");

        conn.close();
    }

    @Test
    void testFindOne() {

        String name = JdbcUtil.findOne(
                "SELECT name FROM product WHERE id = ?",
                rs -> {
                    try {
                        return rs.getString("name");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                },
                1
        );

        assertEquals("Phone", name);
    }

    @Test
    void testFindMany() {

        List<String> list = JdbcUtil.findMany(
                "SELECT name FROM product",
                rs -> {
                    try {
                        return rs.getString("name");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        assertFalse(list.isEmpty());
    }
}
