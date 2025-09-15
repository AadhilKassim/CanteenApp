package com.canteen.dao;

import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/canteen_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
    
    public static void initializeDatabase() {
        try (Connection conn = getConnection()) {
            createTables(conn);
            insertDefaultData(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private static void createTables(Connection conn) throws SQLException {
        String[] createTableQueries = {
            "CREATE TABLE IF NOT EXISTS users (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "register_number VARCHAR(50) UNIQUE NOT NULL, " +
            "password VARCHAR(100) NOT NULL, " +
            "name VARCHAR(100) NOT NULL, " +
            "department VARCHAR(100), " +
            "semester VARCHAR(10), " +
            "is_admin BOOLEAN DEFAULT FALSE)",
            
            "CREATE TABLE IF NOT EXISTS menu_items (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "name VARCHAR(100) NOT NULL, " +
            "price DECIMAL(10,2) NOT NULL)",
            
            "CREATE TABLE IF NOT EXISTS orders (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "user_email VARCHAR(100) NOT NULL, " +
            "total_amount DECIMAL(10,2) NOT NULL, " +
            "payment_method VARCHAR(50) NOT NULL, " +
            "token_number INT NOT NULL, " +
            "order_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
            "status VARCHAR(20) DEFAULT 'PENDING')",
            
            "CREATE TABLE IF NOT EXISTS order_items (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "order_id INT NOT NULL, " +
            "food_id INT NOT NULL, " +
            "food_name VARCHAR(100) NOT NULL, " +
            "quantity INT NOT NULL, " +
            "price DECIMAL(10,2) NOT NULL, " +
            "subtotal DECIMAL(10,2) NOT NULL, " +
            "FOREIGN KEY (order_id) REFERENCES orders(id))"
        };
        
        for (String query : createTableQueries) {
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.execute();
            }
        }
    }
    
    private static void insertDefaultData(Connection conn) throws SQLException {
        String checkUsers = "SELECT COUNT(*) FROM users";
        try (PreparedStatement stmt = conn.prepareStatement(checkUsers);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next() && rs.getInt(1) == 0) {
                String insertUsers = "INSERT INTO users (register_number, password, name, department, semester, is_admin) VALUES " +
                    "('admin', 'admin123', 'Admin User', 'Administration', 'N/A', TRUE), " +
                    "('student123', 'password123', 'John Doe', 'Computer Science', '6th', FALSE)";
                try (PreparedStatement insertStmt = conn.prepareStatement(insertUsers)) {
                    insertStmt.execute();
                }
            }
        }
        
        String checkMenu = "SELECT COUNT(*) FROM menu_items";
        try (PreparedStatement stmt = conn.prepareStatement(checkMenu);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next() && rs.getInt(1) == 0) {
                String insertMenu = "INSERT INTO menu_items (name, price) VALUES " +
                    "('Half Biriyani', 60.00), " +
                    "('Full Biriyani', 120.00), " +
                    "('Meals', 40.00), " +
                    "('Chicken Fry', 60.00)";
                try (PreparedStatement insertStmt = conn.prepareStatement(insertMenu)) {
                    insertStmt.execute();
                }
            }
        }
    }
}