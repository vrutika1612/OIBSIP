package com.oibsip.reservation.service;

import com.oibsip.reservation.dao.UserDAO;
import com.oibsip.reservation.exception.AuthenticationException;
import com.oibsip.reservation.model.User;
import com.oibsip.reservation.util.PasswordUtils;

import java.util.Optional;
import java.util.logging.Logger;

public class AuthenticationService {
    private static final Logger LOGGER = Logger.getLogger(AuthenticationService.class.getName());
    private final UserDAO userDAO;

    public AuthenticationService(UserDAO userDAO) { this.userDAO = userDAO; }

    public User authenticate(String username, char[] password) {
        if (username == null || username.isBlank() || password == null || password.length == 0) {
            throw new AuthenticationException("Username and password are required.");
        }
        Optional<User> user = userDAO.findByUsername(username.trim());
        boolean valid = user.isPresent() && PasswordUtils.verify(new String(password), user.get().passwordHash());
        java.util.Arrays.fill(password, '\0');
        if (!valid) {
            LOGGER.warning("Login failed for username: " + username.trim());
            throw new AuthenticationException("Invalid username or password.");
        }
        LOGGER.info("Login successful for username: " + username.trim());
        return user.get();
    }
}
