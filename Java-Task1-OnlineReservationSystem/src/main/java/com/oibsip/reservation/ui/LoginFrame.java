package com.oibsip.reservation.ui;

import com.oibsip.reservation.dao.ReservationDAO;
import com.oibsip.reservation.dao.TrainDAO;
import com.oibsip.reservation.dao.UserDAO;
import com.oibsip.reservation.service.AuthenticationService;
import com.oibsip.reservation.service.ReservationService;
import com.oibsip.reservation.service.TrainService;
import com.oibsip.reservation.util.UIUtils;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final JTextField usernameField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    private final AuthenticationService authenticationService;

    public LoginFrame() {
        authenticationService = new AuthenticationService(new UserDAO());
        setTitle("Online Reservation System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(460, 330);
        UIUtils.center(this);
        buildUi();
    }

    private void buildUi() {
        JPanel root = UIUtils.panel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Online Reservation System", SwingConstants.CENTER);
        title.setFont(UIUtils.TITLE_FONT);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        root.add(title, gbc);

        gbc.gridwidth = 1; gbc.gridy++;
        root.add(UIUtils.label("Username"), gbc);
        gbc.gridx = 1; root.add(usernameField, gbc);
        gbc.gridx = 0; gbc.gridy++;
        root.add(UIUtils.label("Password"), gbc);
        gbc.gridx = 1; root.add(passwordField, gbc);

        JButton loginButton = new JButton("LOGIN");
        loginButton.setFont(UIUtils.BUTTON_FONT);
        loginButton.addActionListener(e -> login());
        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2;
        root.add(loginButton, gbc);
        getRootPane().setDefaultButton(loginButton);
        add(root);
    }

    private void login() {
        char[] password = passwordField.getPassword();
        try {
            var user = authenticationService.authenticate(usernameField.getText(), password);
            TrainService trainService = new TrainService(new TrainDAO());
            ReservationService reservationService = new ReservationService(new ReservationDAO(), trainService);
            MainFrame mainFrame = new MainFrame(user.username(), reservationService);
            mainFrame.setVisible(true);
            dispose();
        } catch (RuntimeException ex) {
            UIUtils.error(this, ex.getMessage());
            passwordField.setText("");
        } finally {
            java.util.Arrays.fill(password, '\0');
        }
    }
}
