package com.canteen.dao;

import com.canteen.model.FoodItem;
import java.sql.*;
import java.util.*;

public class MenuDAO {
    public List<FoodItem> getAllMenuItems() {
        List<FoodItem> items = new ArrayList<>();
        String query = "SELECT * FROM menu_items ORDER BY id";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                FoodItem item = new FoodItem(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price")
                );
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
    
    public void saveMenuItem(FoodItem item) {
        String query;
        if (item.id == 0) {
            query = "INSERT INTO menu_items (name, price) VALUES (?, ?)";
        } else {
            query = "UPDATE menu_items SET name = ?, price = ? WHERE id = ?";
        }
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, item.name);
            stmt.setDouble(2, item.price);
            
            if (item.id != 0) {
                stmt.setInt(3, item.id);
            }
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteMenuItem(int id) {
        String query = "DELETE FROM menu_items WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}