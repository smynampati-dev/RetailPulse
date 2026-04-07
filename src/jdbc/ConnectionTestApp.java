package com.retailpulse.jdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

public class ConnectionTestApp {

    public static void main(String[] args) throws Exception {

        DataSource ds = HikariDataSourceProvider.getDataSource();

        long start = System.currentTimeMillis();

        Thread[] threads = new Thread[5];

        for (int i = 0; i < 5; i++) {

            threads[i] = new Thread(() -> {
                try {
                    Connection conn = ds.getConnection();
                    Statement stmt = conn.createStatement();

                    stmt.execute("SELECT pg_sleep(2)");

                    System.out.println("Thread done");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        long end = System.currentTimeMillis();

        System.out.println("Total Time: " + (end - start) + " ms");
    }
}
