package com.canteen.dao;

import com.canteen.model.User;
import com.canteen.util.DatabaseConnection;
import java.sql.*;

public class UserDAO {
    
    public User authenticate(String registerNumber, String password) {
        String sql = "SELECT * FROM users WHERE register_number = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, registerNumber);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new User(
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("department"),
                    rs.getString("semester"),
                    rs.getBoolean("is_admin")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database connection failed", e);
        }
        return null;
    }
}