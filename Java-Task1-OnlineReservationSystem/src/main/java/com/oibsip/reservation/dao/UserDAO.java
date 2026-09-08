package com.oibsip.reservation.dao;

import com.oibsip.reservation.db.DatabaseConnection;
import com.oibsip.reservation.exception.DatabaseException;
import com.oibsip.reservation.model.User;

import java.sql.SQLException;
import java.util.Optional;

public class UserDAO {
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT id, username, password_hash FROM users WHERE username = ?";
        try (var connection = DatabaseConnection.getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (var rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new User(rs.getLong("id"), rs.getString("username"), rs.getString("password_hash")));
                }
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Unable to query user", ex);
        }
    }
}
