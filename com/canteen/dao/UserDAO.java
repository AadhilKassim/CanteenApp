package com.canteen.dao;

import com.canteen.model.User;
import java.sql.*;

public class UserDAO {
    public User authenticate(String registerNumber, String password) {
        String query = "SELECT * FROM users WHERE register_number = ? AND password = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, registerNumber);
            stmt.setString(2, password);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        registerNumber + "@college.edu",
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getString("semester"),
                        rs.getBoolean("is_admin")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}