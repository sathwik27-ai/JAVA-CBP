package dao;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BookingDAO {

    // Book a vehicle, optionally with a driverId (nullable)
    public boolean bookVehicle(int customerId, int vehicleId, String startDate, String endDate, Integer driverId) throws SQLException {
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO bookings (customer_id, vehicle_id, start_date, end_date, late_fee, damage_cost, driver_id) " +
                "VALUES (?, ?, ?, ?, 0.00, 0.00, ?)");
            stmt.setInt(1, customerId);
            stmt.setInt(2, vehicleId);
            stmt.setDate(3, Date.valueOf(startDate));
            stmt.setDate(4, Date.valueOf(endDate));
            if (driverId == null) stmt.setNull(5, Types.INTEGER);
            else stmt.setInt(5, driverId);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                try (PreparedStatement update = conn.prepareStatement("UPDATE vehicles SET available = false WHERE id = ?")) {
                    update.setInt(1, vehicleId);
                    update.executeUpdate();
                }
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            throw new SQLException("Booking failed: " + e.getMessage(), e);
        }
    }

    // Return vehicle: mark available, compute late fee, add damage cost
    public boolean returnVehicle(int bookingId, int vehicleId, String actualReturnDate, double perDayLateFee, Double damageCost) throws SQLException {
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            LocalDate actual = LocalDate.parse(actualReturnDate);
            LocalDate end;
            double lateFee = 0.0;

            try (PreparedStatement get = conn.prepareStatement("SELECT end_date FROM bookings WHERE id = ?")) {
                get.setInt(1, bookingId);
                try (ResultSet rs = get.executeQuery()) {
                    if (!rs.next()) {
                        conn.rollback();
                        throw new SQLException("Booking not found for ID: " + bookingId);
                    }
                    end = rs.getDate("end_date").toLocalDate();
                }
            }

            long lateDays = Math.max(0, ChronoUnit.DAYS.between(end, actual));
            lateFee = lateDays * perDayLateFee;
            double dmg = damageCost == null ? 0.0 : damageCost;

            try (PreparedStatement upd = conn.prepareStatement(
                    "UPDATE bookings SET late_fee = ?, damage_cost = ? WHERE id = ?")) {
                upd.setDouble(1, lateFee);
                upd.setDouble(2, dmg);
                upd.setInt(3, bookingId);
                upd.executeUpdate();
            }

            try (PreparedStatement veh = conn.prepareStatement("UPDATE vehicles SET available = true WHERE id = ?")) {
                veh.setInt(1, vehicleId);
                veh.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            throw new SQLException("Return failed: " + e.getMessage(), e);
        }
    }
}