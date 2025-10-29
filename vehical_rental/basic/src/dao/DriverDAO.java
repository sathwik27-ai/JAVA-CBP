package dao;

import model.Driver;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DriverDAO {

    public List<Driver> getAllDrivers() throws SQLException {
        List<Driver> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM drivers");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Driver(rs.getInt("id"), rs.getString("name"), rs.getDouble("rating")));
            }
        } catch (SQLException e) {
            throw new SQLException("Unable to fetch drivers.", e);
        }
        return list;
    }

    public void addDriver(String name, double rating) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO drivers (name, rating) VALUES (?, ?)")) {
            stmt.setString(1, name);
            stmt.setDouble(2, rating);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Unable to add driver.", e);
        }
    }

    public void assignDriverToBooking(int bookingId, int driverId) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE bookings SET driver_id = ? WHERE id = ?")) {
            stmt.setInt(1, driverId);
            stmt.setInt(2, bookingId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Unable to assign driver.", e);
        }
    }

    // Simple average update from feedbacks
    public void updateRatingFromFeedbacks(int driverId) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement avg = conn.prepareStatement("SELECT AVG(rating) AS avg_rating FROM driver_feedback WHERE driver_id = ?")) {
            avg.setInt(1, driverId);
            try (ResultSet rs = avg.executeQuery()) {
                if (rs.next()) {
                    double updated = rs.getDouble("avg_rating");
                    try (PreparedStatement upd = conn.prepareStatement("UPDATE drivers SET rating = ? WHERE id = ?")) {
                        upd.setDouble(1, updated);
                        upd.setInt(2, driverId);
                        upd.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Unable to update driver rating.", e);
        }
    }

    public void addFeedback(int driverId, int bookingId, int rating) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "INSERT INTO driver_feedback (driver_id, booking_id, rating) VALUES (?, ?, ?)")) {
            stmt.setInt(1, driverId);
            stmt.setInt(2, bookingId);
            stmt.setInt(3, rating);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Unable to add feedback.", e);
        }
    }
}