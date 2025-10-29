package dao;

import java.sql.*;

public class CustomerDAO {

    public int addCustomer(String name, String email, String phone) throws SQLException {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO customers (name, email, phone) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
            throw new SQLException("Failed to retrieve generated customer ID.");
        } catch (SQLException e) {
            throw new SQLException("Error adding customer: " + e.getMessage(), e);
        }
    }
}