package com.oibsip.reservation.validation;

import com.oibsip.reservation.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilsTest {
    @Test void requiredRejectsBlank() { assertThrows(ValidationException.class, () -> ValidationUtils.required("  ", "Name")); }
    @Test void positiveIntAcceptsPositiveNumber() { assertEquals(12951, ValidationUtils.positiveInt("12951", "Train number")); }
    @Test void positiveIntRejectsText() { assertThrows(ValidationException.class, () -> ValidationUtils.positiveInt("abc", "Train number")); }
    @Test void numericRejectsLetters() { assertThrows(ValidationException.class, () -> ValidationUtils.numeric("123A", "PNR")); }
    @Test void differentRejectsSameStations() { assertThrows(ValidationException.class, () -> ValidationUtils.different("Ahmedabad", "ahmedabad", "Source", "Destination")); }
}
