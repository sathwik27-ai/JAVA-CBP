package dao;

import model.Vehicle;
import java.sql.*;
import java.util.*;

public class VehicleDAO {

    public List<Vehicle> getAvailableVehicles() throws SQLException {
        List<Vehicle> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM vehicles WHERE available = true");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            throw new SQLException("Unable to fetch available vehicles.", e);
        }
        return list;
    }

    public List<Vehicle> getAllVehicles() throws SQLException {
        List<Vehicle> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM vehicles");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            throw new SQLException("Unable to fetch vehicles.", e);
        }
        return list;
    }

    public void addVehicle(String model, String type, double pricePerDay, boolean available, int capacity) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO vehicles (model, type, price_per_day, available, capacity) VALUES (?, ?, ?, ?, ?)")) {
            stmt.setString(1, model);
            stmt.setString(2, type);
            stmt.setDouble(3, pricePerDay);
            stmt.setBoolean(4, available);
            stmt.setInt(5, capacity);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Unable to add vehicle.", e);
        }
    }

    public void markVehicleAvailable(int vehicleId, boolean available) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE vehicles SET available = ? WHERE id = ?")) {
            stmt.setBoolean(1, available);
            stmt.setInt(2, vehicleId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Unable to update vehicle availability.", e);
        }
    }

    public List<Vehicle> getVehiclesByCapacity(int capacity) throws SQLException {
        List<Vehicle> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM vehicles WHERE capacity = ? AND available = true")) {
            stmt.setInt(1, capacity);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        } catch (SQLException e) {
            throw new SQLException("Unable to fetch vehicles by capacity.", e);
        }
        return list;
    }

    private Vehicle map(ResultSet rs) throws SQLException {
        return new Vehicle(
            rs.getInt("id"),
            rs.getString("model"),
            rs.getString("type"),
            rs.getDouble("price_per_day"),
            rs.getBoolean("available"),
            rs.getInt("capacity")
        );
    }
}