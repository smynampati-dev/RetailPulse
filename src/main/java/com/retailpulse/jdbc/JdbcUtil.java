package com.retailpulse.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class JdbcUtil {

    private static final String URL =
            System.getProperty("db.url", "jdbc:postgresql://localhost:5432/retailpulse_db");

    private static final String USER =
            System.getProperty("db.user", "retailpulse");

    private static final String PASSWORD =
            System.getProperty("db.password", "retailpulse123");

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // ================================
    // EXECUTE (Object... args)
    // ================================
    public static void execute(String query, Object... args) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            for (int i = 0; i < args.length; i++) {
                stmt.setObject(i + 1, args[i]);
            }

            stmt.execute();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ================================
    // EXECUTE (Consumer)
    // ================================
    public static void execute(String query, Consumer<PreparedStatement> consumer) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            consumer.accept(stmt);
            stmt.execute();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ================================
    // FIND ONE
    // ================================
    public static <T> T findOne(String query, Function<ResultSet, T> mapper, Object... args) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            for (int i = 0; i < args.length; i++) {
                stmt.setObject(i + 1, args[i]);
            }

            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) return null;

            T result = mapper.apply(rs);

            if (rs.next()) {
                throw new RuntimeException("More than one result found");
            }

            return result;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ================================
    // FIND MANY
    // ================================
    public static <T> List<T> findMany(String query, Function<ResultSet, T> mapper, Object... args) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            for (int i = 0; i < args.length; i++) {
                stmt.setObject(i + 1, args[i]);
            }

            ResultSet rs = stmt.executeQuery();
            List<T> list = new ArrayList<>();

            while (rs.next()) {
                list.add(mapper.apply(rs));
            }

            return list;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
