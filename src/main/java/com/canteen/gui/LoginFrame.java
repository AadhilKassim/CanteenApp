package com.canteen.gui;

import com.canteen.dao.UserDAO;
import com.canteen.model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JTextField registerField;
    private JPasswordField passwordField;
    private UserDAO userDAO;
    
    public LoginFrame() {
        userDAO = new UserDAO();
        initializeComponents();
    }
    
    private void initializeComponents() {
        setTitle("College Canteen - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Title
        JLabel titleLabel = new JLabel("College Canteen Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(titleLabel, gbc);
        
        // Register Number
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Register Number:"), gbc);
        
        registerField = new JTextField(20);
        gbc.gridx = 1;
        add(registerField, gbc);
        
        // Password
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Password:"), gbc);
        
        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        add(passwordField, gbc);
        
        // Login Button
        JButton loginButton = new JButton("Login");
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        add(loginButton, gbc);
        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
    }
    
    private void login() {
        String registerNumber = registerField.getText();
        String password = new String(passwordField.getPassword());
        
        if (registerNumber.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both register number and password");
            return;
        }
        
        User user = authenticateUser(registerNumber, password);
        if (user != null) {
            dispose();
            if (user.isAdmin()) {
                new AdminFrame(user).setVisible(true);
            } else {
                new BookingFrame(user).setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials");
        }
    }
    
    private User authenticateUser(String registerNumber, String password) {
        try {
            return userDAO.authenticate(registerNumber, password);
        } catch (Exception e) {
            // Database not connected, use default credentials
            if ("admin".equals(registerNumber) && "admin123".equals(password)) {
                return new User("admin@college.edu", "admin123", "Admin User", "Administration", "N/A", true);
            } else if ("student123".equals(registerNumber) && "password123".equals(password)) {
                return new User("student@college.edu", "password123", "John Doe", "Computer Science", "6th", false);
            } else if ("student456".equals(registerNumber) && "password123".equals(password)) {
                return new User("jane@college.edu", "password123", "Jane Smith", "Electronics", "4th", false);
            }
            return null;
        }
    }
}