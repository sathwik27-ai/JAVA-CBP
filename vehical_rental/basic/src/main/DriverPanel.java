package main;

import dao.DriverDAO;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DriverPanel extends Frame {
    public DriverPanel() {
        setTitle("Driver Management");
        setSize(600, 420);
        setLayout(null);
        setVisible(true);

        Label addLabel = new Label("Add Driver");
        addLabel.setBounds(40, 40, 100, 25);
        Label nameLabel = new Label("Name:");
        nameLabel.setBounds(40, 70, 80, 25);
        TextField nameField = new TextField();
        nameField.setBounds(130, 70, 180, 25);

        Label ratingLabel = new Label("Initial Rating (0-5):");
        ratingLabel.setBounds(320, 70, 140, 25);
        TextField ratingField = new TextField("5.0");
        ratingField.setBounds(470, 70, 80, 25);

        Button addBtn = new Button("Add");
        addBtn.setBounds(130, 100, 80, 25);

        Label feedbackLabel = new Label("Add Feedback");
        feedbackLabel.setBounds(40, 160, 100, 25);
        Label driverIdLabel = new Label("Driver ID:");
        driverIdLabel.setBounds(40, 190, 80, 25);
        TextField driverIdField = new TextField();
        driverIdField.setBounds(130, 190, 80, 25);

        Label bookingIdLabel = new Label("Booking ID:");
        bookingIdLabel.setBounds(220, 190, 80, 25);
        TextField bookingIdField = new TextField();
        bookingIdField.setBounds(310, 190, 80, 25);

        Label fbRatingLabel = new Label("Rating (1-5):");
        fbRatingLabel.setBounds(400, 190, 80, 25);
        TextField fbRatingField = new TextField();
        fbRatingField.setBounds(490, 190, 60, 25);

        Button addFeedbackBtn = new Button("Submit Feedback");
        addFeedbackBtn.setBounds(130, 220, 120, 25);

        Button updateRatingBtn = new Button("Update Avg Rating");
        updateRatingBtn.setBounds(260, 220, 140, 25);

        TextArea statusArea = new TextArea();
        statusArea.setBounds(40, 260, 510, 120);
        statusArea.setEditable(false);

        add(addLabel); add(nameLabel); add(nameField); add(ratingLabel); add(ratingField); add(addBtn);
        add(feedbackLabel); add(driverIdLabel); add(driverIdField);
        add(bookingIdLabel); add(bookingIdField);
        add(fbRatingLabel); add(fbRatingField);
        add(addFeedbackBtn); add(updateRatingBtn);
        add(statusArea);

        DriverDAO driverDAO = new DriverDAO();

        addBtn.addActionListener(e -> {
            try {
                driverDAO.addDriver(nameField.getText().trim(), Double.parseDouble(ratingField.getText().trim()));
                statusArea.append("Driver added.\n");
            } catch (Exception ex) {
                statusArea.append("Error adding driver: " + ex.getMessage() + "\n");
            }
        });

        addFeedbackBtn.addActionListener(e -> {
            try {
                int driverId = Integer.parseInt(driverIdField.getText().trim());
                int bookingId = Integer.parseInt(bookingIdField.getText().trim());
                int rating = Integer.parseInt(fbRatingField.getText().trim());
                driverDAO.addFeedback(driverId, bookingId, rating);
                statusArea.append("Feedback recorded.\n");
            } catch (Exception ex) {
                statusArea.append("Error feedback: " + ex.getMessage() + "\n");
            }
        });

        updateRatingBtn.addActionListener(e -> {
            try {
                int driverId = Integer.parseInt(driverIdField.getText().trim());
                driverDAO.updateRatingFromFeedbacks(driverId);
                statusArea.append("Driver rating updated from feedbacks.\n");
            } catch (Exception ex) {
                statusArea.append("Error updating rating: " + ex.getMessage() + "\n");
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { dispose(); }
        });
    }
}