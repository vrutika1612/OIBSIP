package com.oibsip.reservation.db;

import com.oibsip.reservation.util.PasswordUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class DatabaseInitializer {
    private static final Logger LOGGER = Logger.getLogger(DatabaseInitializer.class.getName());
    private DatabaseInitializer() {}

    public static void initialize() throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                executeScript(statement, "schema.sql");
                executeScript(statement, "data.sql");
            }
            seedDemoUser(connection);
            connection.commit();
            LOGGER.info("Database initialization completed");
        } catch (SQLException | RuntimeException ex) {
            LOGGER.log(Level.SEVERE, "Database initialization failed", ex);
            throw ex;
        }
    }

    private static void seedDemoUser(Connection connection) throws SQLException {
        try (var check = connection.prepareStatement("SELECT COUNT(*) FROM users");
             var result = check.executeQuery()) {
            if (result.next() && result.getInt(1) == 0) {
                String hash = PasswordUtils.hash("admin123");
                try (var insert = connection.prepareStatement("INSERT INTO users(username, password_hash) VALUES (?, ?)");) {
                    insert.setString(1, "admin");
                    insert.setString(2, hash);
                    insert.executeUpdate();
                    LOGGER.info("Created demo user 'admin'");
                }
            }
        }
    }

    private static void executeScript(Statement statement, String resource) {
        try (InputStream input = DatabaseInitializer.class.getClassLoader().getResourceAsStream(resource)) {
            if (input == null) throw new IllegalStateException("Missing database resource: " + resource);
            String script = new String(input.readAllBytes(), StandardCharsets.UTF_8);
            for (String sql : script.split(";")) {
                String trimmed = sql.trim();
                if (!trimmed.isEmpty()) statement.execute(trimmed);
            }
        } catch (IOException | SQLException ex) {
            throw new IllegalStateException("Unable to execute database script: " + resource, ex);
        }
    }
}
