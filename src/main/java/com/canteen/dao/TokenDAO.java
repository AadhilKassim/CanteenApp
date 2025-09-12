package com.canteen.dao;

import com.canteen.model.Token;
import com.canteen.util.DatabaseConnection;
import java.sql.*;
import java.time.LocalDateTime;

public class TokenDAO {
    
    public boolean saveToken(Token token) {
        String sql = "INSERT INTO tokens (token_number, user_email, food_details, total_amount, payment_method, order_time, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, token.getTokenNumber());
            stmt.setString(2, token.getUserEmail());
            stmt.setString(3, token.getFoodDetails());
            stmt.setDouble(4, token.getTotalAmount());
            stmt.setString(5, token.getPaymentMethod());
            stmt.setTimestamp(6, Timestamp.valueOf(token.getOrderTime()));
            stmt.setString(7, token.getStatus());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public String generateTokenNumber() {
        return String.valueOf(System.currentTimeMillis() % 100000);
    }
}