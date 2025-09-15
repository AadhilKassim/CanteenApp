package com.canteen.dao;

import com.canteen.model.Order;
import com.canteen.model.OrderItem;
import java.sql.*;
import java.util.*;

public class OrderDAO {
    public int saveOrder(Order order) {
        String orderQuery = "INSERT INTO orders (user_email, total_amount, payment_method, token_number, status) VALUES (?, ?, ?, ?, ?)";
        String itemQuery = "INSERT INTO order_items (order_id, food_id, food_name, quantity, price, subtotal) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            
            int orderId;
            try (PreparedStatement orderStmt = conn.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS)) {
                orderStmt.setString(1, order.getUserEmail());
                orderStmt.setDouble(2, order.getTotalAmount());
                orderStmt.setString(3, order.getPaymentMethod());
                orderStmt.setInt(4, order.getTokenNumber());
                orderStmt.setString(5, order.getStatus());
                orderStmt.executeUpdate();
                
                try (ResultSet rs = orderStmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        orderId = rs.getInt(1);
                    } else {
                        throw new SQLException("Failed to get order ID");
                    }
                }
            }
            
            try (PreparedStatement itemStmt = conn.prepareStatement(itemQuery)) {
                for (OrderItem item : order.getItems()) {
                    itemStmt.setInt(1, orderId);
                    itemStmt.setInt(2, item.getFoodId());
                    itemStmt.setString(3, item.getFoodName());
                    itemStmt.setInt(4, item.getQuantity());
                    itemStmt.setDouble(5, item.getPrice());
                    itemStmt.setDouble(6, item.getSubtotal());
                    itemStmt.addBatch();
                }
                itemStmt.executeBatch();
            }
            
            conn.commit();
            return orderId;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders ORDER BY order_time DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                int orderId = rs.getInt("id");
                List<OrderItem> items = getOrderItems(orderId);
                
                Order order = new Order(
                    orderId,
                    rs.getString("user_email"),
                    items,
                    rs.getDouble("total_amount"),
                    rs.getString("payment_method"),
                    rs.getInt("token_number")
                );
                order.setStatus(rs.getString("status"));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
    
    private List<OrderItem> getOrderItems(int orderId) {
        List<OrderItem> items = new ArrayList<>();
        String query = "SELECT * FROM order_items WHERE order_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrderItem item = new OrderItem(
                        rs.getInt("food_id"),
                        rs.getString("food_name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                    );
                    items.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
    
    public Order getOrderById(int orderId) {
        String query = "SELECT * FROM orders WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    List<OrderItem> items = getOrderItems(orderId);
                    
                    Order order = new Order(
                        orderId,
                        rs.getString("user_email"),
                        items,
                        rs.getDouble("total_amount"),
                        rs.getString("payment_method"),
                        rs.getInt("token_number")
                    );
                    order.setStatus(rs.getString("status"));
                    return order;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}