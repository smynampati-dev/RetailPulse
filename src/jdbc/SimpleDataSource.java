package com.retailpulse.jdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;

public class SimpleDataSource implements DataSource {

    private static final String URL = "jdbc:postgresql://localhost:5432/retailpulse_db";
    private static final String USER = "retailpulse";
    private static final String PASSWORD = "retailpulse123";

    private Connection connection;

    public SimpleDataSource() {
        try {
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Connection getConnection() {
        return connection; // SAME connection every time ❌
    }

    @Override
    public Connection getConnection(String username, String password) {
        return getConnection();
    }

    // Leave others unimplemented
}

