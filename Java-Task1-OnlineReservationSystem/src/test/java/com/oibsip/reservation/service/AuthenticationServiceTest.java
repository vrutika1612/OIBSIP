package com.oibsip.reservation.service;

import com.oibsip.reservation.dao.UserDAO;
import com.oibsip.reservation.db.DatabaseInitializer;
import com.oibsip.reservation.exception.AuthenticationException;
import org.junit.jupiter.api.*;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationServiceTest {
    private static Path db;
    private AuthenticationService service;

    @BeforeAll static void setupDatabase() throws Exception {
        db = Files.createTempFile("oibsip-auth-", ".db");
        System.setProperty("db.url", "jdbc:sqlite:" + db.toAbsolutePath());
        DatabaseInitializer.initialize();
    }
    @AfterAll static void cleanup() throws Exception { Files.deleteIfExists(db); System.clearProperty("db.url"); }
    @BeforeEach void setup() { service = new AuthenticationService(new UserDAO()); }

    @Test void validLoginSucceeds() { assertEquals("admin", service.authenticate("admin", "admin123".toCharArray()).username()); }
    @Test void invalidUsernameFails() { assertThrows(AuthenticationException.class, () -> service.authenticate("missing", "admin123".toCharArray())); }
    @Test void invalidPasswordFails() { assertThrows(AuthenticationException.class, () -> service.authenticate("admin", "wrong".toCharArray())); }
    @Test void emptyUsernameFails() { assertThrows(AuthenticationException.class, () -> service.authenticate("", "admin123".toCharArray())); }
    @Test void emptyPasswordFails() { assertThrows(AuthenticationException.class, () -> service.authenticate("admin", new char[0])); }
}
