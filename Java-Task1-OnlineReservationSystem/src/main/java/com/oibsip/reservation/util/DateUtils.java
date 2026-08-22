package com.oibsip.reservation.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class DateUtils {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private DateUtils() {}

    public static LocalDate parse(String value) {
        try { return LocalDate.parse(value.trim(), FORMATTER); }
        catch (DateTimeParseException ex) { throw new IllegalArgumentException("Date must use dd-MM-yyyy format"); }
    }

    public static String format(LocalDate date) { return FORMATTER.format(date); }
}
