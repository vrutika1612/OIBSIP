package com.oibsip.reservation.ui;

import com.oibsip.reservation.exception.TrainNotFoundException;
import com.oibsip.reservation.model.Reservation;
import com.oibsip.reservation.model.Train;
import com.oibsip.reservation.service.ReservationService;
import com.oibsip.reservation.service.TrainService;
import com.oibsip.reservation.util.DateUtils;
import com.oibsip.reservation.util.UIUtils;
import com.oibsip.reservation.validation.ValidationUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.time.LocalDate;

public class ReservationPanel extends JPanel {
    private final ReservationService reservationService;
    private final TrainService trainService;
    private final JTextField passengerNameField = new JTextField();
    private final JTextField trainNumberField = new JTextField();
    private final JTextField trainNameField = new JTextField();
    private final JComboBox<String> classTypeBox = new JComboBox<>(new String[]{"AC", "Sleeper", "Second Sitting"});
    private final JTextField journeyDateField = new JTextField();
    private final JTextField sourceField = new JTextField();
    private final JTextField destinationField = new JTextField();
    private final JLabel trainStatus = new JLabel("Enter a train number to load train details.");
    private final Timer trainLookupTimer;

    public ReservationPanel(ReservationService reservationService) {
        this.reservationService = reservationService;
        this.trainService = new TrainService(new com.oibsip.reservation.dao.TrainDAO());
        setLayout(new BorderLayout());
        add(createTitle(), BorderLayout.NORTH);
        add(createForm(), BorderLayout.CENTER);
        trainNameField.setEditable(false);
        trainLookupTimer = new Timer(300, e -> lookupTrain());
        trainLookupTimer.setRepeats(false);
        trainNumberField.getDocument().addDocumentListener(new DocumentListener() {
            private void schedule() { trainLookupTimer.restart(); }
            public void insertUpdate(DocumentEvent e) { schedule(); }
            public void removeUpdate(DocumentEvent e) { schedule(); }
            public void changedUpdate(DocumentEvent e) { schedule(); }
        });
    }

    private JLabel createTitle() {
        JLabel title = new JLabel("Book Reservation");
        title.setFont(UIUtils.TITLE_FONT);
        title.setBorder(BorderFactory.createEmptyBorder(18, 18, 8, 18));
        return title;
    }

    private JPanel createForm() {
        JPanel form = UIUtils.panel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 7, 7, 7);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        String[] labels = {"Passenger Name", "Train Number", "Train Name", "Class Type", "Journey Date (dd-MM-yyyy)", "Source Station", "Destination Station"};
        JComponent[] fields = {passengerNameField, trainNumberField, trainNameField, classTypeBox, journeyDateField, sourceField, destinationField};
        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0; gbc.gridy = i; gbc.weightx = 0;
            form.add(UIUtils.label(labels[i]), gbc);
            gbc.gridx = 1; gbc.weightx = 1;
            form.add(fields[i], gbc);
        }
        gbc.gridx = 1; gbc.gridy = labels.length;
        trainStatus.setFont(new Font("SansSerif", Font.ITALIC, 12));
        form.add(trainStatus, gbc);
        JButton bookButton = new JButton("BOOK / INSERT");
        bookButton.setFont(UIUtils.BUTTON_FONT);
        bookButton.addActionListener(e -> book());
        gbc.gridy++;
        form.add(bookButton, gbc);
        return form;
    }

    private void lookupTrain() {
        String value = trainNumberField.getText().trim();
        if (value.isEmpty()) { trainNameField.setText(""); trainStatus.setText("Enter a train number to load train details."); return; }
        if (!value.matches("\\d+")) { trainNameField.setText(""); trainStatus.setText("Train number must be numeric."); return; }
        try {
            Train train = trainService.findRequired(Integer.parseInt(value));
            trainNameField.setText(train.trainName());
            if (sourceField.getText().isBlank()) sourceField.setText(train.source());
            if (destinationField.getText().isBlank()) destinationField.setText(train.destination());
            trainStatus.setText("Train found: " + train.trainName());
        } catch (TrainNotFoundException | NumberFormatException ex) {
            trainNameField.setText("");
            trainStatus.setText("Train not found.");
        }
    }

    private void book() {
        try {
            String passenger = ValidationUtils.required(passengerNameField.getText(), "Passenger name");
            int trainNumber = ValidationUtils.positiveInt(trainNumberField.getText(), "Train number");
            String classType = (String) classTypeBox.getSelectedItem();
            LocalDate date = DateUtils.parse(ValidationUtils.required(journeyDateField.getText(), "Journey date"));
            String source = ValidationUtils.required(sourceField.getText(), "Source station");
            String destination = ValidationUtils.required(destinationField.getText(), "Destination station");
            Reservation reservation = reservationService.book(passenger, trainNumber, classType, date, source, destination);
            UIUtils.info(this, formatConfirmation(reservation), "Booking Successful");
            clearForm();
        } catch (RuntimeException ex) {
            UIUtils.error(this, ex.getMessage());
        }
    }

    private String formatConfirmation(Reservation r) {
        return "Booking Successful\n\n" + "PNR: " + r.pnr() + "\n" + "Passenger: " + r.passengerName() + "\n" +
                "Train Number: " + r.trainNumber() + "\n" + "Train Name: " + r.trainName() + "\n" +
                "Class: " + r.classType() + "\n" + "Journey Date: " + DateUtils.format(r.journeyDate()) + "\n" +
                "Source: " + r.sourceStation() + "\n" + "Destination: " + r.destinationStation();
    }

    private void clearForm() {
        passengerNameField.setText(""); trainNumberField.setText(""); trainNameField.setText("");
        journeyDateField.setText(""); sourceField.setText(""); destinationField.setText("");
        classTypeBox.setSelectedIndex(0);
    }
}
