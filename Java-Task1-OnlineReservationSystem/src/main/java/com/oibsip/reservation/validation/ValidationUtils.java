package com.oibsip.reservation.validation;

import com.oibsip.reservation.exception.ValidationException;

import java.time.LocalDate;

public final class ValidationUtils {
    private ValidationUtils() {}

    public static String required(String value, String field) {
        if (value == null || value.isBlank()) throw new ValidationException(field + " is required.");
        return value.trim();
    }

    public static int positiveInt(String value, String field) {
        String input = required(value, field);
        try {
            int number = Integer.parseInt(input);
            if (number <= 0) throw new NumberFormatException();
            return number;
        } catch (NumberFormatException ex) {
            throw new ValidationException(field + " must be a positive number.");
        }
    }

    public static String numeric(String value, String field) {
        String input = required(value, field);
        if (!input.matches("\\d+")) throw new ValidationException(field + " must contain digits only.");
        return input;
    }

    public static void different(String first, String second, String firstName, String secondName) {
        if (first.equalsIgnoreCase(second)) throw new ValidationException(firstName + " and " + secondName + " cannot be identical.");
    }

    public static void journeyDate(LocalDate date) {
        if (date == null) throw new ValidationException("Journey date is required.");
        if (date.isBefore(LocalDate.now())) throw new ValidationException("Journey date cannot be in the past.");
    }
}
