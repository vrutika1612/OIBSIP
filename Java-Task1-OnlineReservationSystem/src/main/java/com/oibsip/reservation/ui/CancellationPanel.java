package com.oibsip.reservation.ui;

import com.oibsip.reservation.model.Reservation;
import com.oibsip.reservation.service.ReservationService;
import com.oibsip.reservation.util.DateUtils;
import com.oibsip.reservation.util.UIUtils;
import com.oibsip.reservation.validation.ValidationUtils;

import javax.swing.*;
import java.awt.*;

public class CancellationPanel extends JPanel {
    private final ReservationService reservationService;
    private final JTextField pnrField = new JTextField(15);
    private final JTextArea detailsArea = new JTextArea(10, 45);
    private Reservation fetchedReservation;

    public CancellationPanel(ReservationService reservationService) {
        this.reservationService = reservationService;
        setLayout(new BorderLayout());
        add(createTitle(), BorderLayout.NORTH);
        add(createContent(), BorderLayout.CENTER);
    }

    private JLabel createTitle() {
        JLabel title = new JLabel("Cancel Reservation");
        title.setFont(UIUtils.TITLE_FONT);
        title.setBorder(BorderFactory.createEmptyBorder(18, 18, 8, 18));
        return title;
    }

    private JPanel createContent() {
        JPanel root = UIUtils.panel(new BorderLayout(12, 12));
        JPanel search = new JPanel(new FlowLayout(FlowLayout.LEFT));
        search.add(UIUtils.label("PNR Number"));
        search.add(pnrField);
        JButton fetch = new JButton("FETCH");
        fetch.addActionListener(e -> fetchBooking());
        search.add(fetch);
        root.add(search, BorderLayout.NORTH);
        detailsArea.setEditable(false);
        detailsArea.setLineWrap(true);
        detailsArea.setWrapStyleWord(true);
        root.add(new JScrollPane(detailsArea), BorderLayout.CENTER);
        JButton cancel = new JButton("CONFIRM CANCELLATION");
        cancel.setFont(UIUtils.BUTTON_FONT);
        cancel.addActionListener(e -> cancelBooking());
        root.add(cancel, BorderLayout.SOUTH);
        return root;
    }

    private void fetchBooking() {
        try {
            String pnr = ValidationUtils.numeric(pnrField.getText(), "PNR");
            if (pnr.length() != 10) throw new com.oibsip.reservation.exception.ValidationException("PNR must contain exactly 10 digits.");
            fetchedReservation = reservationService.findByPnr(pnr).orElse(null);
            if (fetchedReservation == null) {
                detailsArea.setText("");
                UIUtils.error(this, "Booking not found.");
                return;
            }
            detailsArea.setText(format(fetchedReservation));
        } catch (RuntimeException ex) {
            UIUtils.error(this, ex.getMessage());
        }
    }

    private void cancelBooking() {
        if (fetchedReservation == null) {
            UIUtils.error(this, "Fetch a valid booking before cancellation.");
            return;
        }
        if (!UIUtils.confirm(this, "Are you sure you want to cancel this booking?")) return;
        try {
            reservationService.cancelByPnr(fetchedReservation.pnr());
            UIUtils.info(this, "Booking cancelled successfully.\nPNR: " + fetchedReservation.pnr(), "Cancellation Successful");
            clear();
        } catch (RuntimeException ex) {
            UIUtils.error(this, ex.getMessage());
        }
    }

    private String format(Reservation r) {
        return "PNR: " + r.pnr() + "\nPassenger: " + r.passengerName() + "\nTrain Number: " + r.trainNumber() +
                "\nTrain Name: " + r.trainName() + "\nClass: " + r.classType() + "\nJourney Date: " + DateUtils.format(r.journeyDate()) +
                "\nSource: " + r.sourceStation() + "\nDestination: " + r.destinationStation();
    }

    private void clear() { pnrField.setText(""); detailsArea.setText(""); fetchedReservation = null; }
}
