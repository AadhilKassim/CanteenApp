# College Canteen Management System

A Java Swing application with MySQL backend for managing canteen orders and token generation.

## Setup Instructions

### 1. Database Setup
1. Install MySQL Server
2. Run the SQL script: `mysql -u root -p < database_setup.sql`
3. Update database credentials in `DatabaseConnection.java` if needed

### 2. Dependencies
MySQL Connector/J is located at: `C:\Program Files\MySQL\MySQL Connector\mysql-connector-j-9.3.0`

### 3. Compilation & Execution
```bash
# Compile (or run compile.bat)
javac -cp "C:\Program Files\MySQL\MySQL Connector\mysql-connector-j-9.3.0\mysql-connector-j-9.3.0.jar" com/canteen/*.java com/canteen/*/*.java

# Run (or run run.bat)
java -cp ".;C:\Program Files\MySQL\MySQL Connector\mysql-connector-j-9.3.0\mysql-connector-j-9.3.0.jar" com.canteen.CanteenApp
```

## Features

### Admin Features
- Login with admin credentials
- Manage menu items (Add/Update/Delete)
- View all orders and tokens

### Student Features
- Login with student credentials
- Browse menu and add items to cart
- Select payment method and complete purchase
- Generate and print token

## Login Credentials
- **Admin**: `admin` / `admin123`
- **Student**: `student123` / `password123`

## Database Schema
- **users**: User authentication and profile data
- **menu_items**: Available food items and prices
- **orders**: Order records with payment details
- **order_items**: Individual items within each order

## Application Flow
1. Login → Menu Selection → Payment → Token Generation
2. Admin can manage menu items and view order history
3. All purchases are tracked with unique token numbers