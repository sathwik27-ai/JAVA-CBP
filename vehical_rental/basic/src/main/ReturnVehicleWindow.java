package main;

import dao.BookingDAO;
import dao.VehicleDAO;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ReturnVehicleWindow extends Frame {
    public ReturnVehicleWindow() {
        setTitle("Return Vehicle (Admin)");
        setSize(520, 320);
        setLayout(null);
        setVisible(true);

        Label bookingLabel = new Label("Booking ID:");
        TextField bookingField = new TextField();
        Label vehicleLabel = new Label("Vehicle ID:");
        TextField vehicleField = new TextField();
        Label returnDateLabel = new Label("Actual Return Date (YYYY-MM-DD):");
        TextField returnDateField = new TextField();
        Label lateFeeLabel = new Label("Late Fee per Day (₹):");
        TextField lateFeeField = new TextField("500"); // example default
        Label damageLabel = new Label("Damage Cost (₹):");
        TextField damageField = new TextField("0");
        Button submitButton = new Button("Process Return");
        Label statusLabel = new Label("", Label.CENTER);

        bookingLabel.setBounds(40, 50, 150, 25); bookingField.setBounds(210, 50, 260, 25);
        vehicleLabel.setBounds(40, 85, 150, 25); vehicleField.setBounds(210, 85, 260, 25);
        returnDateLabel.setBounds(40, 120, 220, 25); returnDateField.setBounds(270, 120, 200, 25);
        lateFeeLabel.setBounds(40, 155, 150, 25); lateFeeField.setBounds(210, 155, 260, 25);
        damageLabel.setBounds(40, 190, 150, 25); damageField.setBounds(210, 190, 260, 25);
        submitButton.setBounds(210, 225, 120, 30);
        statusLabel.setBounds(60, 260, 360, 25);

        add(bookingLabel); add(bookingField);
        add(vehicleLabel); add(vehicleField);
        add(returnDateLabel); add(returnDateField);
        add(lateFeeLabel); add(lateFeeField);
        add(damageLabel); add(damageField);
        add(submitButton); add(statusLabel);

        submitButton.addActionListener(e -> {
            try {
                int bookingId = Integer.parseInt(bookingField.getText().trim());
                int vehicleId = Integer.parseInt(vehicleField.getText().trim());
                String actualReturnDate = returnDateField.getText().trim();
                double perDayLateFee = Double.parseDouble(lateFeeField.getText().trim());
                double damageCost = Double.parseDouble(damageField.getText().trim());

                boolean ok = new BookingDAO().returnVehicle(bookingId, vehicleId, actualReturnDate, perDayLateFee, damageCost);
                if (ok) {
                    new VehicleDAO().markVehicleAvailable(vehicleId, true);
                    statusLabel.setForeground(new Color(0, 128, 0));
                    statusLabel.setText("Return processed successfully.");
                } else {
                    statusLabel.setForeground(Color.RED);
                    statusLabel.setText("Return failed.");
                }
            } catch (Exception ex) {
                statusLabel.setForeground(Color.RED);
                statusLabel.setText("Error: " + ex.getMessage());
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { dispose(); }
        });
    }
}