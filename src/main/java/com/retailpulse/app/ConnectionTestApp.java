package com.retailpulse.app;

import com.retailpulse.jdbc.SingleConnectionDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import com.retailpulse.config.HikariDataSourceConfig;
public class ConnectionTestApp {

    public static void main(String[] args) throws Exception {



        DataSource dataSource = HikariDataSourceConfig.getDataSource();

        int threads = 5;

        long start = System.currentTimeMillis();

        Thread[] workers = new Thread[threads];

        for (int i = 0; i < threads; i++) {
            workers[i] = new Thread(() -> {
                try (Connection conn = dataSource.getConnection();
                     Statement stmt = conn.createStatement()) {

                    stmt.execute("SELECT pg_sleep(2)");

                    System.out.println("Thread done: " + Thread.currentThread().getName());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            workers[i].start();
        }

        for (Thread t : workers) {
            t.join();
        }

        long end = System.currentTimeMillis();

        System.out.println("⏱ Total time: " + (end - start) + " ms");
    }
}
