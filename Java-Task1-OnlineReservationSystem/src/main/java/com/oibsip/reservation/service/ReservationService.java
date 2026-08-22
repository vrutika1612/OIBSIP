package com.oibsip.reservation.service;

import com.oibsip.reservation.dao.ReservationDAO;
import com.oibsip.reservation.db.DatabaseConnection;
import com.oibsip.reservation.exception.ReservationException;
import com.oibsip.reservation.exception.ValidationException;
import com.oibsip.reservation.model.Reservation;
import com.oibsip.reservation.model.Train;
import com.oibsip.reservation.util.PnrGenerator;
import com.oibsip.reservation.validation.ValidationUtils;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReservationService {
    private static final int PNR_ATTEMPTS = 10;
    private static final Logger LOGGER = Logger.getLogger(ReservationService.class.getName());
    private final ReservationDAO reservationDAO;
    private final TrainService trainService;

    public ReservationService(ReservationDAO reservationDAO, TrainService trainService) {
        this.reservationDAO = reservationDAO;
        this.trainService = trainService;
    }

    public Reservation book(String passengerName, int trainNumber, String classType, LocalDate journeyDate,
                            String sourceStation, String destinationStation) {
        String passenger = ValidationUtils.required(passengerName, "Passenger name");
        String clazz = ValidationUtils.required(classType, "Class type");
        String source = ValidationUtils.required(sourceStation, "Source station");
        String destination = ValidationUtils.required(destinationStation, "Destination station");
        ValidationUtils.journeyDate(journeyDate);
        ValidationUtils.different(source, destination, "Source", "Destination");

        Train train = trainService.findRequired(trainNumber);
        for (int attempt = 1; attempt <= PNR_ATTEMPTS; attempt++) {
            String pnr = PnrGenerator.next();
            Reservation reservation = new Reservation(pnr, passenger, train.trainNumber(), train.trainName(), clazz,
                    journeyDate, source, destination);
            try (var connection = DatabaseConnection.getConnection()) {
                connection.setAutoCommit(false);
                try {
                    if (reservationDAO.insert(connection, reservation)) {
                        connection.commit();
                        LOGGER.info("Booking created successfully for train " + trainNumber);
                        return reservation;
                    }
                    connection.rollback();
                } catch (RuntimeException ex) {
                    rollbackQuietly(connection);
                    throw ex;
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "Booking transaction failed", ex);
                throw new ReservationException("Booking could not be completed.", ex);
            }
        }
        throw new ReservationException("Unable to generate a unique PNR. Please try again.");
    }

    private void rollbackQuietly(java.sql.Connection connection) {
        try { connection.rollback(); } catch (SQLException rollbackEx) {
            LOGGER.log(Level.WARNING, "Transaction rollback failed", rollbackEx);
        }
    }

    public Optional<Reservation> findByPnr(String pnr) {
        String numericPnr = ValidationUtils.numeric(pnr, "PNR");
        if (numericPnr.length() != 10) throw new ValidationException("PNR must contain exactly 10 digits.");
        return reservationDAO.findByPnr(numericPnr);
    }

    public void cancelByPnr(String pnr) {
        String numericPnr = ValidationUtils.numeric(pnr, "PNR");
        if (numericPnr.length() != 10) throw new ValidationException("PNR must contain exactly 10 digits.");
        if (!reservationDAO.deleteByPnr(numericPnr)) throw new ReservationException("Booking not found.");
        LOGGER.info("Booking cancelled successfully for PNR: " + numericPnr);
    }
}
