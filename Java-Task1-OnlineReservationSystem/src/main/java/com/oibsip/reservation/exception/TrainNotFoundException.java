package com.oibsip.reservation.exception;

public class TrainNotFoundException extends ReservationException {
    public TrainNotFoundException(int trainNumber) { super("Train not found: " + trainNumber); }
}
