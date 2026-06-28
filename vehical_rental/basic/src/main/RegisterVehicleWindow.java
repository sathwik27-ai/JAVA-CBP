package main;

import dao.VehicleDAO;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RegisterVehicleWindow extends Frame {
    private final Runnable onSuccess;

    public RegisterVehicleWindow(Runnable onSuccess) {
        this.onSuccess = onSuccess;
        setTitle("Register New Vehicle");
        setSize(420, 360);
        setLayout(null);
        setVisible(true);

        Font labelFont = new Font("SansSerif", Font.BOLD, 14);

        Label modelLabel = new Label("Model:");
        TextField modelField = new TextField();
        Label typeLabel = new Label("Type:");
        TextField typeField = new TextField();
        Label priceLabel = new Label("Price/Day:");
        TextField priceField = new TextField();
        Label capacityLabel = new Label("Capacity (2/5/6/7):");
        TextField capacityField = new TextField();
        Checkbox availableCheckbox = new Checkbox("Available", true);
        Button submitButton = new Button("Register");
        Label statusLabel = new Label("", Label.CENTER);

        modelLabel.setFont(labelFont); typeLabel.setFont(labelFont); priceLabel.setFont(labelFont); capacityLabel.setFont(labelFont);

        modelLabel.setBounds(40, 50, 140, 25); modelField.setBounds(200, 50, 180, 25);
        typeLabel.setBounds(40, 90, 140, 25); typeField.setBounds(200, 90, 180, 25);
        priceLabel.setBounds(40, 130, 140, 25); priceField.setBounds(200, 130, 180, 25);
        capacityLabel.setBounds(40, 170, 160, 25); capacityField.setBounds(200, 170, 180, 25);
        availableCheckbox.setBounds(200, 205, 100, 25);
        submitButton.setBounds(200, 240, 100, 30);
        statusLabel.setBounds(60, 280, 300, 25);

        add(modelLabel); add(modelField);
        add(typeLabel); add(typeField);
        add(priceLabel); add(priceField);
        add(capacityLabel); add(capacityField);
        add(availableCheckbox); add(submitButton); add(statusLabel);

        submitButton.addActionListener(e -> {
            try {
                String model = modelField.getText().trim();
                String type = typeField.getText().trim();
                double price = Double.parseDouble(priceField.getText().trim());
                int capacity = Integer.parseInt(capacityField.getText().trim());
                boolean available = availableCheckbox.getState();

                new VehicleDAO().addVehicle(model, type, price, available, capacity);
                statusLabel.setForeground(new Color(0, 128, 0));
                statusLabel.setText("Vehicle registered.");
                if (onSuccess != null) onSuccess.run();
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