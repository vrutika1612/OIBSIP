package com.oibsip.reservation.ui;

import com.oibsip.reservation.model.Reservation;
import com.oibsip.reservation.service.ReservationService;
import com.oibsip.reservation.util.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.util.function.Supplier;

public class MainFrame extends JFrame {
    private final String username;
    private final ReservationService reservationService;
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel content = new JPanel(cardLayout);

    public MainFrame(String username, ReservationService reservationService) {
        this.username = username;
        this.reservationService = reservationService;
        setTitle("Online Reservation System - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 620);
        UIUtils.center(this);
        buildUi();
    }

    private void buildUi() {
        JPanel root = new JPanel(new BorderLayout());
        root.add(createHeader(), BorderLayout.NORTH);
        ReservationPanel reservationPanel = new ReservationPanel(reservationService);
        CancellationPanel cancellationPanel = new CancellationPanel(reservationService);
        content.add(createDashboard(), "dashboard");
        content.add(reservationPanel, "reservation");
        content.add(cancellationPanel, "cancellation");
        root.add(content, BorderLayout.CENTER);
        root.add(createNavigation(), BorderLayout.WEST);
        add(root);
    }

    private JPanel createHeader() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(14, 18, 14, 18));
        JLabel title = new JLabel("Online Reservation System");
        title.setFont(UIUtils.TITLE_FONT);
        JLabel user = new JLabel("Logged in: " + username);
        panel.add(title, BorderLayout.WEST);
        panel.add(user, BorderLayout.EAST);
        return panel;
    }

    private JPanel createNavigation() {
        JPanel nav = UIUtils.panel(new GridLayout(4, 1, 8, 8));
        JButton dashboard = new JButton("Dashboard");
        JButton book = new JButton("Book Reservation");
        JButton cancel = new JButton("Cancel Reservation");
        JButton logout = new JButton("Logout");
        dashboard.addActionListener(e -> cardLayout.show(content, "dashboard"));
        book.addActionListener(e -> cardLayout.show(content, "reservation"));
        cancel.addActionListener(e -> cardLayout.show(content, "cancellation"));
        logout.addActionListener(e -> logout());
        nav.add(dashboard); nav.add(book); nav.add(cancel); nav.add(logout);
        return nav;
    }

    private JPanel createDashboard() {
        JPanel panel = UIUtils.panel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel welcome = new JLabel("Welcome, " + username);
        welcome.setFont(UIUtils.TITLE_FONT);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(welcome, gbc);
        gbc.gridy++;
        panel.add(new JLabel("Use the navigation menu to book or cancel a reservation."), gbc);
        return panel;
    }

    private void logout() {
        if (UIUtils.confirm(this, "Are you sure you want to logout?")) {
            new LoginFrame().setVisible(true);
            dispose();
        }
    }
}
