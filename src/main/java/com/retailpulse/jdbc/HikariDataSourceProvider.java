package com.retailpulse.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class HikariDataSourceProvider {

    private static HikariDataSource dataSource;

    public static DataSource getDataSource() {

        if (dataSource == null) {
            HikariConfig config = new HikariConfig();

            config.setJdbcUrl("jdbc:postgresql://localhost:5432/retailpulse_db");
            config.setUsername("retailpulse");
            config.setPassword("retailpulse123");

            config.setMaximumPoolSize(5); // pool size

            dataSource = new HikariDataSource(config);
        }

        return dataSource;
    }
}