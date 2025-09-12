package com.canteen.gui;

import com.canteen.dao.FoodItemDAO;
import com.canteen.model.FoodItem;
import com.canteen.model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminFrame extends JFrame {
    private User currentUser;
    private FoodItemDAO foodItemDAO;
    
    public AdminFrame(User user) {
        this.currentUser = user;
        this.foodItemDAO = new FoodItemDAO();
        initializeComponents();
    }
    
    private void initializeComponents() {
        setTitle("Admin Panel - Food Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Title
        JLabel titleLabel = new JLabel("Food Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(titleLabel, gbc);
        
        // Food name
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Food Name:"), gbc);
        
        JTextField nameField = new JTextField(20);
        gbc.gridx = 1;
        add(nameField, gbc);
        
        // Price
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Price:"), gbc);
        
        JTextField priceField = new JTextField(20);
        gbc.gridx = 1;
        add(priceField, gbc);
        
        // Quantity
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Quantity:"), gbc);
        
        JTextField quantityField = new JTextField(20);
        gbc.gridx = 1;
        add(quantityField, gbc);
        
        // Type
        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Type:"), gbc);
        
        JComboBox<String> typeCombo = new JComboBox<>(new String[]{"VEG", "NON-VEG"});
        gbc.gridx = 1;
        add(typeCombo, gbc);
        
        // Add button
        JButton addButton = new JButton("Add Food Item");
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        add(addButton, gbc);
        
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText();
                    double price = Double.parseDouble(priceField.getText());
                    int quantity = Integer.parseInt(quantityField.getText());
                    String type = (String) typeCombo.getSelectedItem();
                    
                    if (name.isEmpty()) {
                        JOptionPane.showMessageDialog(AdminFrame.this, "Please enter food name");
                        return;
                    }
                    
                    FoodItem item = new FoodItem(0, name, price, quantity, quantity, type);
                    if (foodItemDAO.addFoodItem(item)) {
                        JOptionPane.showMessageDialog(AdminFrame.this, "Food item added successfully");
                        nameField.setText("");
                        priceField.setText("");
                        quantityField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(AdminFrame.this, "Failed to add food item");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(AdminFrame.this, "Please enter valid price and quantity");
                }
            }
        });
    }
}