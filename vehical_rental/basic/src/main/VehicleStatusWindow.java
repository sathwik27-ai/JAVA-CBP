package main;

import dao.VehicleDAO;
import model.Vehicle;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class VehicleStatusWindow extends Frame {

    public VehicleStatusWindow() {
        setTitle("Vehicle Status");
        setSize(600, 450);
        setLayout(new BorderLayout());
        setVisible(true);

        TextArea statusArea = new TextArea();
        statusArea.setEditable(false);
        add(statusArea, BorderLayout.CENTER);

        try {
            List<Vehicle> vehicles = new VehicleDAO().getAllVehicles();
            for (Vehicle v : vehicles) statusArea.append(v.getDisplayText() + "\n");
        } catch (Exception ex) {
            statusArea.setText("Error: " + ex.getMessage());
        }

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { dispose(); }
        });
    }
}