package main;

import dao.VehicleDAO;
import model.Vehicle;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class RecommendationWindow extends Frame {
    public RecommendationWindow() {
        setTitle("Recommended Vehicles by Capacity");
        setSize(560, 420);
        setLayout(null);
        setVisible(true);

        Label capacityLabel = new Label("Capacity (2/5/6/7):");
        TextField capacityField = new TextField();
        Button searchButton = new Button("Search");
        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);

        capacityLabel.setBounds(40, 50, 150, 25);
        capacityField.setBounds(200, 50, 120, 25);
        searchButton.setBounds(330, 50, 80, 25);
        resultArea.setBounds(40, 90, 470, 280);

        add(capacityLabel); add(capacityField);
        add(searchButton); add(resultArea);

        searchButton.addActionListener(e -> {
            try {
                int capacity = Integer.parseInt(capacityField.getText().trim());
                List<Vehicle> vehicles = new VehicleDAO().getVehiclesByCapacity(capacity);
                resultArea.setText("");
                if (vehicles.isEmpty()) {
                    resultArea.setText("No vehicles found for capacity: " + capacity);
                } else {
                    for (Vehicle v : vehicles) resultArea.append(v.getDisplayText() + "\n");
                }
            } catch (Exception ex) {
                resultArea.setText("Error: " + ex.getMessage());
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { dispose(); }
        });
    }
}