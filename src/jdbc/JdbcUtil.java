package com.retailpulse.jdbc;

import java.sql.*;
import java.util.*;
import java.util.function.Function;

public class JdbcUtil {

    private static final String URL = "jdbc:postgresql://localhost:5432/retailpulse_db";
    private static final String USER = "retailpulse";
    private static final String PASSWORD = "retailpulse123";

    // 🔹 Get Connection
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // =====================================================
    // 1. EXECUTE (NO RESULT)
    // =====================================================
    public static void execute(String query, Object... args) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // =====================================================
    // 2. FIND ONE
    // =====================================================
    public static <T> T findOne(String query,
                                Function<ResultSet, T> mapper,
                                Object... args) {

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                return null;
            }

            T result = mapper.apply(rs);

            if (rs.next()) {
                throw new RuntimeException("More than one result found!");
            }

            return result;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    // =====================================================
// 1B. EXECUTE WITH CONSUMER (FLEXIBLE)
// =====================================================
    public static void execute(String query,
                               java.util.function.Consumer<PreparedStatement> consumer) {

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            consumer.accept(ps);
            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // =====================================================
    // 3. FIND MANY
    // =====================================================
    public static <T> List<T> findMany(String query,
                                       Function<ResultSet, T> mapper,
                                       Object... args) {

        List<T> results = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                results.add(mapper.apply(rs));
            }

            return results;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
