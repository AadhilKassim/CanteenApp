-- MySQL Database Setup for Canteen Application
CREATE DATABASE IF NOT EXISTS canteen_db;
USE canteen_db;

-- Users table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    register_number VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(100) NOT NULL,
    department VARCHAR(100),
    semester VARCHAR(10),
    is_admin BOOLEAN DEFAULT FALSE
);

-- Menu items table
CREATE TABLE IF NOT EXISTS menu_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL
);

-- Orders table
CREATE TABLE IF NOT EXISTS orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_email VARCHAR(100) NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    token_number INT NOT NULL,
    order_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'PENDING'
);

-- Order items table
CREATE TABLE IF NOT EXISTS order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    food_id INT NOT NULL,
    food_name VARCHAR(100) NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id)
);

-- Insert default users
INSERT INTO users (register_number, password, name, department, semester, is_admin) VALUES 
('admin', 'admin123', 'Admin User', 'Administration', 'N/A', TRUE),
('student123', 'password123', 'John Doe', 'Computer Science', '6th', FALSE);

-- Insert default menu items
INSERT INTO menu_items (name, price) VALUES 
('Half Biriyani', 60.00),
('Full Biriyani', 120.00),
('Meals', 40.00),
('Chicken Fry', 60.00);