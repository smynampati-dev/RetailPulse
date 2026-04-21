package com.retailpulse.rdbmsdemo;

import com.retailpulse.config.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class IsolationLevelDemo {

    public static void main(String[] args) {

        Thread user1 = new Thread(() -> transaction1());
        Thread user2 = new Thread(() -> transaction2());

        user1.start();
        user2.start();
    }

    // 🔵 Transaction 1 (Reader)
    public static void transaction1() {
        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();

            System.out.println("🔵 T1: Reading stock...");

            ResultSet rs1 = stmt.executeQuery("SELECT stock_quantity FROM product WHERE id = 1");
            if (rs1.next()) {
                System.out.println("🔵 T1: Initial Stock = " + rs1.getInt(1));
            }

            // Wait so T2 updates in between
            Thread.sleep(3000);

            ResultSet rs2 = stmt.executeQuery("SELECT stock_quantity FROM product WHERE id = 1");
            if (rs2.next()) {
                System.out.println("🔵 T1: After Delay Stock = " + rs2.getInt(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔴 Transaction 2 (Writer)
    public static void transaction2() {
        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();

            Thread.sleep(1000);

            System.out.println("🔴 T2: Updating stock...");

            stmt.executeUpdate("UPDATE product SET stock_quantity = stock_quantity - 5 WHERE id = 1");

            conn.commit();

            System.out.println("🔴 T2: Update committed!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
