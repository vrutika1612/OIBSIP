package com.oibsip.reservation.service;

import com.oibsip.reservation.dao.ReservationDAO;
import com.oibsip.reservation.dao.TrainDAO;
import com.oibsip.reservation.db.DatabaseInitializer;
import com.oibsip.reservation.exception.ReservationException;
import com.oibsip.reservation.exception.TrainNotFoundException;
import com.oibsip.reservation.exception.ValidationException;
import org.junit.jupiter.api.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {
    private static Path db;
    private ReservationService service;
    private ReservationDAO reservationDAO;

    @BeforeAll static void setupDatabase() throws Exception {
        db = Files.createTempFile("oibsip-reservation-", ".db");
        System.setProperty("db.url", "jdbc:sqlite:" + db.toAbsolutePath());
        DatabaseInitializer.initialize();
    }
    @AfterAll static void cleanup() throws Exception { Files.deleteIfExists(db); System.clearProperty("db.url"); }
    @BeforeEach void setup() { reservationDAO = new ReservationDAO(); service = new ReservationService(reservationDAO, new TrainService(new TrainDAO())); }

    @Test void successfulReservationCanBeFetchedAndCancelled() {
        var reservation = service.book("Rahul Patel", 12951, "AC", LocalDate.now().plusDays(5), "Ahmedabad", "Mumbai");
        assertEquals(10, reservation.pnr().length());
        assertTrue(reservationDAO.findByPnr(reservation.pnr()).isPresent());
        service.cancelByPnr(reservation.pnr());
        assertTrue(reservationDAO.findByPnr(reservation.pnr()).isEmpty());
    }

    @Test void unknownTrainFails() { assertThrows(TrainNotFoundException.class, () -> service.book("Rahul", 99999, "AC", LocalDate.now().plusDays(1), "A", "B")); }
    @Test void pastDateFails() { assertThrows(ValidationException.class, () -> service.book("Rahul", 12951, "AC", LocalDate.now().minusDays(1), "A", "B")); }
    @Test void blankPassengerFails() { assertThrows(ValidationException.class, () -> service.book(" ", 12951, "AC", LocalDate.now().plusDays(1), "A", "B")); }
    @Test void invalidPnrFails() { assertThrows(ValidationException.class, () -> service.findByPnr("abc")); }
    @Test void missingCancellationFails() { assertThrows(ReservationException.class, () -> service.cancelByPnr("1234567890")); }
}
