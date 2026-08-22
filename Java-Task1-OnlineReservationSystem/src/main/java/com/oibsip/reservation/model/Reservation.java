package com.oibsip.reservation.model;

import java.time.LocalDate;

public record Reservation(String pnr, String passengerName, int trainNumber, String trainName,
                          String classType, LocalDate journeyDate, String sourceStation,
                          String destinationStation) {
    public Reservation withPnr(String newPnr) {
        return new Reservation(newPnr, passengerName, trainNumber, trainName, classType,
                journeyDate, sourceStation, destinationStation);
    }
}
