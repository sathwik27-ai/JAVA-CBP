package main;

import dao.*;
import model.Vehicle;
import model.Driver;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class Main extends Frame {
    private Choice vehicleChoice;
    private Choice capacityChoice;
    private Choice driverChoice;
    private TextField nameField, emailField, phoneField, startDateField, endDateField;
    private Label statusLabel;

    private VehicleDAO vehicleDAO = new VehicleDAO();
    private CustomerDAO customerDAO = new CustomerDAO();
    private BookingDAO bookingDAO = new BookingDAO();
    private DriverDAO driverDAO = new DriverDAO();

    public Main() {
        setTitle("Vehicle Rental System");
        setSize(640, 700);
        setLayout(null);
        setBackground(new Color(240, 248, 255));
        setVisible(true);

        Font labelFont = new Font("SansSerif", Font.BOLD, 14);
        Font inputFont = new Font("SansSerif", Font.PLAIN, 14);

        Label header = new Label("Book Your Ride", Label.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 20));
        header.setBounds(170, 30, 300, 30);
        header.setForeground(new Color(0, 102, 204));
        add(header);

        // Vehicle list
        Label vehicleLabel = new Label("Select Vehicle:");
        vehicleLabel.setFont(labelFont);
        vehicleLabel.setBounds(60, 90, 150, 25);
        add(vehicleLabel);

        vehicleChoice = new Choice();
        vehicleChoice.setFont(inputFont);
        vehicleChoice.setBounds(220, 90, 360, 25);
        add(vehicleChoice);
        refreshVehicles(); // Populate

        // Capacity recommendation
        Label capacityLabel = new Label("Recommend by capacity:");
        capacityLabel.setFont(labelFont);
        capacityLabel.setBounds(60, 130, 150, 25);
        add(capacityLabel);

        capacityChoice = new Choice();
        capacityChoice.setFont(inputFont);
        capacityChoice.setBounds(220, 130, 120, 25);
        capacityChoice.add("2");
        capacityChoice.add("5");
        capacityChoice.add("6");
        capacityChoice.add("7");
        add(capacityChoice);

        Button filterButton = new Button("Filter");
        filterButton.setBounds(350, 130, 80, 25);
        add(filterButton);
        filterButton.addActionListener(e -> filterByCapacity());

        // Driver selection
        Label driverLabel = new Label("Rent a driver (optional):");
        driverLabel.setFont(labelFont);
        driverLabel.setBounds(60, 170, 160, 25);
        add(driverLabel);

        driverChoice = new Choice();
        driverChoice.setFont(inputFont);
        driverChoice.setBounds(220, 170, 360, 25);
        add(driverChoice);
        refreshDrivers();

        // Customer fields
        nameField = createLabeledField("Name:", 210);
        emailField = createLabeledField("Email:", 250);
        phoneField = createLabeledField("Phone:", 290);
        startDateField = createLabeledField("Start Date (YYYY-MM-DD):", 330);
        endDateField = createLabeledField("End Date (YYYY-MM-DD):", 370);

        Button bookButton = new Button("Book Vehicle");
        bookButton.setFont(labelFont);
        bookButton.setBackground(new Color(0, 153, 76));
        bookButton.setForeground(Color.WHITE);
        bookButton.setBounds(220, 420, 150, 35);
        add(bookButton);

        statusLabel = new Label("", Label.CENTER);
        statusLabel.setFont(labelFont);
        statusLabel.setBounds(140, 470, 360, 30);
        add(statusLabel);

        // Admin / Extra windows
        Button registerVehicleBtn = new Button("Register Vehicle");
        registerVehicleBtn.setBounds(60, 520, 140, 30);
        add(registerVehicleBtn);
        registerVehicleBtn.addActionListener(e -> new RegisterVehicleWindow(this::refreshVehicles));

        Button vehicleStatusBtn = new Button("Vehicle Status");
        vehicleStatusBtn.setBounds(220, 520, 140, 30);
        add(vehicleStatusBtn);
        vehicleStatusBtn.addActionListener(e -> new VehicleStatusWindow());

        Button returnVehicleBtn = new Button("Return Vehicle");
        returnVehicleBtn.setBounds(380, 520, 140, 30);
        add(returnVehicleBtn);
        returnVehicleBtn.addActionListener(e -> new ReturnVehicleWindow());

        Button driverPanelBtn = new Button("Driver Panel");
        driverPanelBtn.setBounds(540, 520, 80, 30);
        add(driverPanelBtn);
        driverPanelBtn.addActionListener(e -> new DriverPanel());

        // Book action
        bookButton.addActionListener(e -> {
            try {
                int vehicleId = Integer.parseInt(vehicleChoice.getSelectedItem().split(" - ")[0]);
                String name = nameField.getText().trim();
                String email = emailField.getText().trim();
                String phone = phoneField.getText().trim();
                String start = startDateField.getText().trim();
                String end = endDateField.getText().trim();

                if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || start.isEmpty() || end.isEmpty()) {
                    showError("All fields are required.");
                    return;
                }

                int customerId = customerDAO.addCustomer(name, email, phone);

                Integer driverId = null;
                String driverSel = driverChoice.getSelectedItem();
                if (driverSel != null && !driverSel.isEmpty()) {
                    driverId = Integer.parseInt(driverSel.split(" - ")[0]);
                }

                boolean booked = bookingDAO.bookVehicle(customerId, vehicleId, start, end, driverId);

                statusLabel.setForeground(booked ? new Color(0, 128, 0) : Color.RED);
                statusLabel.setText(booked ? "Booking successful!" : "Booking failed.");
                if (booked) refreshVehicles();
            } catch (Exception ex) {
                showError("Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { dispose(); }
        });
    }

    private TextField createLabeledField(String labelText, int y) {
        Label label = new Label(labelText);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setBounds(60, y, 200, 25);
        add(label);

        TextField field = new TextField();
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setBounds(270, y, 310, 25);
        add(field);
        return field;
    }

    private void showError(String message) {
        statusLabel.setForeground(Color.RED);
        statusLabel.setText(message);
    }

    private void refreshVehicles() {
        vehicleChoice.removeAll();
        try {
            List<Vehicle> vehicles = vehicleDAO.getAvailableVehicles();
            for (Vehicle v : vehicles) vehicleChoice.add(v.getDisplayText());
        } catch (SQLException e) {
            showError("Error loading vehicles: " + e.getMessage());
        }
    }

    private void filterByCapacity() {
        vehicleChoice.removeAll();
        try {
            int capacity = Integer.parseInt(capacityChoice.getSelectedItem());
            List<Vehicle> vehicles = vehicleDAO.getVehiclesByCapacity(capacity);
            for (Vehicle v : vehicles) vehicleChoice.add(v.getDisplayText());
        } catch (Exception e) {
            showError("Error filtering vehicles: " + e.getMessage());
        }
    }

    private void refreshDrivers() {
        driverChoice.removeAll();
        try {
            List<Driver> drivers = driverDAO.getAllDrivers();
            driverChoice.add(""); // optional none
            for (Driver d : drivers) driverChoice.add(d.toString());
        } catch (SQLException e) {
            showError("Error loading drivers: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}