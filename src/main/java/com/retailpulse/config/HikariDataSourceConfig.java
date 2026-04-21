package com.retailpulse.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class HikariDataSourceConfig {

    public static DataSource getDataSource() {

        HikariConfig config = new HikariConfig();

        // ✅ USE SAME CONFIG AS DBConnection
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/retailpulse_db");
        config.setUsername("retailpulse");
        config.setPassword("retailpulse123");

        // 🔥 POOL SETTINGS
        config.setMaximumPoolSize(5);

        return new HikariDataSource(config);
    }
}