package com.oibsip.reservation.util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public final class UIUtils {
    public static final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 24);
    public static final Font LABEL_FONT = new Font("SansSerif", Font.PLAIN, 14);
    public static final Font BUTTON_FONT = new Font("SansSerif", Font.BOLD, 14);
    private UIUtils() {}

    public static JPanel panel() { return panel(new BorderLayout(12, 12)); }
    public static JPanel panel(LayoutManager layout) {
        JPanel panel = new JPanel(layout);
        panel.setBorder(new EmptyBorder(18, 18, 18, 18));
        return panel;
    }
    public static JLabel label(String text) { JLabel label = new JLabel(text); label.setFont(LABEL_FONT); return label; }
    public static void center(Window window) { window.setLocationRelativeTo(null); }
    public static void info(Component parent, String message, String title) { JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE); }
    public static void error(Component parent, String message) { JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE); }
    public static boolean confirm(Component parent, String message) { return JOptionPane.showConfirmDialog(parent, message, "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION; }
}
