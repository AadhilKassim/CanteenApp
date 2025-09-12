# College Canteen Token Management System

A Java Swing application for managing food orders in a college canteen with MySQL backend.

## Features

### Client Side (Student Interface)
- Login with pre-created credentials
- Browse available food items with prices and quantities
- Select quantity and add items to order
- Payment processing with multiple payment methods (Credit Card, Debit Card, Net Banking, UPI)
- Token generation with order details

### Admin Side (Canteen Management)
- Admin login
- Add new food items with price and quantity limits
- Manage food inventory

## Prerequisites

- Java 8 or higher
- MySQL Server
- MySQL JDBC Driver (mysql-connector-java)

## Setup Instructions

1. **Database Setup**
   - Install MySQL Server
   - Run the `database_setup.sql` script to create the database and tables
   - Update database credentials in `DatabaseConnection.java` if needed

2. **JDBC Driver**
   - Download MySQL Connector/J from MySQL website
   - Add the JAR file to your classpath

3. **Compile and Run**
   ```bash
   # Compile
   javac -cp ".:mysql-connector-java-8.0.33.jar" src/main/java/com/canteen/*.java src/main/java/com/canteen/*/*.java
   
   # Run
   java -cp ".:mysql-connector-java-8.0.33.jar:src/main/java" com.canteen.CanteenApplication
   ```

## Default Login Credentials

### Student Accounts
- Register Number: `student123`, Password: `password123`
- Register Number: `student456`, Password: `password123`

### Admin Account
- Register Number: `admin`, Password: `admin123`

**Note**: These credentials work both with database connection and as fallback when database is not available.

## Project Structure

```
src/main/java/com/canteen/
├── CanteenApplication.java          # Main application entry point
├── model/
│   ├── User.java                    # User model class
│   ├── FoodItem.java               # Food item model class
│   └── Token.java                  # Token model class
├── dao/
│   ├── UserDAO.java                # User database operations
│   ├── FoodItemDAO.java            # Food item database operations
│   └── TokenDAO.java               # Token database operations
├── gui/
│   ├── LoginFrame.java             # Login interface
│   ├── BookingFrame.java           # Food ordering interface
│   ├── PaymentFrame.java           # Payment processing interface
│   ├── TokenFrame.java             # Token display interface
│   └── AdminFrame.java             # Admin management interface
└── util/
    └── DatabaseConnection.java     # Database connection utility
```

## Technologies Used

- **Frontend**: Java Swing, AWT
- **Backend**: JDBC-connected MySQL Database
- **Architecture**: Object-Oriented Programming with DAO pattern
- **Database**: MySQL

## Usage

1. Start the application
2. Login with provided credentials
3. **For Students**: Browse food items, add to cart, proceed to payment, and receive token
4. **For Admins**: Add new food items and manage inventory

The system generates unique tokens for each order containing food details, amount, and payment method for easy order tracking.