package com.oibsip.reservation.dao;

import com.oibsip.reservation.db.DatabaseConnection;
import com.oibsip.reservation.exception.DatabaseException;
import com.oibsip.reservation.model.Reservation;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public class ReservationDAO {
    public boolean insert(Connection connection, Reservation reservation) {
        String sql = "INSERT INTO reservations(pnr, passenger_name, train_number, class_type, journey_date, source_station, destination_station) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (var statement = connection.prepareStatement(sql)) {
            statement.setString(1, reservation.pnr());
            statement.setString(2, reservation.passengerName());
            statement.setInt(3, reservation.trainNumber());
            statement.setString(4, reservation.classType());
            statement.setString(5, reservation.journeyDate().toString());
            statement.setString(6, reservation.sourceStation());
            statement.setString(7, reservation.destinationStation());
            statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            if ("23505".equals(ex.getSQLState()) || ex.getMessage().toLowerCase().contains("unique")) return false;
            throw new DatabaseException("Unable to insert reservation", ex);
        }
    }

    public Optional<Reservation> findByPnr(String pnr) {
        String sql = "SELECT r.pnr, r.passenger_name, r.train_number, t.train_name, r.class_type, r.journey_date, r.source_station, r.destination_station FROM reservations r JOIN trains t ON t.train_number = r.train_number WHERE r.pnr = ?";
        try (var connection = DatabaseConnection.getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setString(1, pnr);
            try (var rs = statement.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(map(rs));
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Unable to retrieve reservation", ex);
        }
    }

    public boolean deleteByPnr(String pnr) {
        String sql = "DELETE FROM reservations WHERE pnr = ?";
        try (var connection = DatabaseConnection.getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setString(1, pnr);
            return statement.executeUpdate() == 1;
        } catch (SQLException ex) {
            throw new DatabaseException("Unable to cancel reservation", ex);
        }
    }

    public Connection openConnection() throws SQLException { return DatabaseConnection.getConnection(); }

    private Reservation map(java.sql.ResultSet rs) throws SQLException {
        return new Reservation(rs.getString("pnr"), rs.getString("passenger_name"), rs.getInt("train_number"),
                rs.getString("train_name"), rs.getString("class_type"), LocalDate.parse(rs.getString("journey_date")),
                rs.getString("source_station"), rs.getString("destination_station"));
    }
}
