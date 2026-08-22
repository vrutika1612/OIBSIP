package com.oibsip.reservation.dao;

import com.oibsip.reservation.db.DatabaseInitializer;
import org.junit.jupiter.api.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import com.oibsip.reservation.model.Reservation;

class ReservationDAOTest {
    private static Path db;
    @BeforeAll static void setup() throws Exception {
        db = Files.createTempFile("oibsip-dao-", ".db");
        System.setProperty("db.url", "jdbc:sqlite:" + db.toAbsolutePath());
        DatabaseInitializer.initialize();
    }
    @AfterAll static void cleanup() throws Exception { Files.deleteIfExists(db); System.clearProperty("db.url"); }

    @Test void insertAndFindWork() throws Exception {
        ReservationDAO dao = new ReservationDAO();
        Reservation r = new Reservation("1234567890", "Test User", 12951, "Mumbai Rajdhani", "AC", LocalDate.now().plusDays(2), "Ahmedabad", "Mumbai");
        try (Connection c = dao.openConnection()) {
            c.setAutoCommit(false);
            assertTrue(dao.insert(c, r));
            c.commit();
        }
        assertTrue(dao.findByPnr("1234567890").isPresent());
        assertTrue(dao.deleteByPnr("1234567890"));
    }
}
