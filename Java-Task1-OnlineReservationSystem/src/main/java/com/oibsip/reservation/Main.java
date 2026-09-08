package com.oibsip.reservation;

import com.oibsip.reservation.db.DatabaseInitializer;
import com.oibsip.reservation.ui.LoginFrame;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private Main() {}

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                DatabaseInitializer.initialize();
                new LoginFrame().setVisible(true);
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, "Application startup failed", ex);
                JOptionPane.showMessageDialog(null, "Unable to start the application. Check the application logs.",
                        "Startup Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
