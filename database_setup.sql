-- Create database
CREATE DATABASE IF NOT EXISTS canteen_db;
USE canteen_db;

-- Create users table
CREATE TABLE users (
    register_number VARCHAR(20) PRIMARY KEY,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(100) NOT NULL,
    department VARCHAR(50) NOT NULL,
    semester VARCHAR(10) NOT NULL,
    is_admin BOOLEAN DEFAULT FALSE
);

-- Create food_items table
CREATE TABLE food_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    available_quantity INT NOT NULL,
    total_quantity INT NOT NULL,
    type VARCHAR(10) NOT NULL
);

-- Create tokens table
CREATE TABLE tokens (
    token_number VARCHAR(20) PRIMARY KEY,
    user_register_number VARCHAR(20) NOT NULL,
    food_details TEXT NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    order_time TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL,
    FOREIGN KEY (user_register_number) REFERENCES users(register_number)
);

-- Insert sample users
INSERT INTO users (register_number, email, password, name, department, semester, is_admin) VALUES
('student123', 'student@college.edu', 'password123', 'John Doe', 'Computer Science', '6th', FALSE),
('admin', 'admin@college.edu', 'admin123', 'Admin User', 'Administration', 'N/A', TRUE),
('student456', 'jane@college.edu', 'password123', 'Jane Smith', 'Electronics', '4th', FALSE);

-- Insert sample food items
INSERT INTO food_items (name, price, available_quantity, total_quantity, type) VALUES
('Biriyani', 60.00, 45, 50, 'NON-VEG'),
('Meals', 50.00, 70, 70, 'VEG'),
('Chicken Roast/Fry', 40.00, 30, 30, 'NON-VEG');