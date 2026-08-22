package com.oibsip.reservation.dao;

import com.oibsip.reservation.db.DatabaseConnection;
import com.oibsip.reservation.exception.DatabaseException;
import com.oibsip.reservation.model.Train;

import java.sql.SQLException;
import java.util.Optional;

public class TrainDAO {
    public Optional<Train> findByTrainNumber(int trainNumber) {
        String sql = "SELECT train_number, train_name, source, destination FROM trains WHERE train_number = ?";
        try (var connection = DatabaseConnection.getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, trainNumber);
            try (var rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Train(rs.getInt("train_number"), rs.getString("train_name"),
                            rs.getString("source"), rs.getString("destination")));
                }
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Unable to query train", ex);
        }
    }
}
