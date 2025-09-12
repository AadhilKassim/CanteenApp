package com.canteen.gui;

import com.canteen.dao.FoodItemDAO;
import com.canteen.dao.TokenDAO;
import com.canteen.model.FoodItem;
import com.canteen.model.Token;
import com.canteen.model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.List;

public class BookingFrame extends JFrame {
    private User currentUser;
    private FoodItemDAO foodItemDAO;
    private TokenDAO tokenDAO;
    private JPanel foodPanel;
    private double totalAmount = 0;
    private StringBuilder orderDetails = new StringBuilder();
    
    public BookingFrame(User user) {
        this.currentUser = user;
        this.foodItemDAO = new FoodItemDAO();
        this.tokenDAO = new TokenDAO();
        initializeComponents();
        loadFoodItems();
    }
    
    private void initializeComponents() {
        setTitle("College Canteen Token Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Title
        JLabel titleLabel = new JLabel("College Canteen Token Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);
        
        // Food items panel
        foodPanel = new JPanel();
        foodPanel.setLayout(new BoxLayout(foodPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(foodPanel);
        add(scrollPane, BorderLayout.CENTER);
        
        // Buy button
        JButton buyButton = new JButton("Buy Tokens");
        buyButton.addActionListener(e -> proceedToPayment());
        add(buyButton, BorderLayout.SOUTH);
    }
    
    private void loadFoodItems() {
        List<FoodItem> items = foodItemDAO.getAllFoodItems();
        for (FoodItem item : items) {
            JPanel itemPanel = createFoodItemPanel(item);
            foodPanel.add(itemPanel);
        }
        foodPanel.revalidate();
    }
    
    private JPanel createFoodItemPanel(FoodItem item) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(item.getName()));
        
        JLabel priceLabel = new JLabel("Price: ₹" + (int)item.getPrice() + " per token");
        JLabel remainingLabel = new JLabel(item.getName() + " Tokens Remaining: " + item.getAvailableQuantity() + "/" + item.getTotalQuantity());
        
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.add(priceLabel);
        infoPanel.add(remainingLabel);
        
        JPanel quantityPanel = new JPanel(new FlowLayout());
        quantityPanel.add(new JLabel("Quantity:"));
        JTextField quantityField = new JTextField(5);
        quantityPanel.add(quantityField);
        
        JButton addButton = new JButton("Add to Order");
        addButton.addActionListener(e -> {
            try {
                int quantity = Integer.parseInt(quantityField.getText());
                if (quantity > 0 && quantity <= item.getAvailableQuantity()) {
                    addToOrder(item, quantity);
                    quantityField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid quantity");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number");
            }
        });
        
        panel.add(infoPanel, BorderLayout.WEST);
        panel.add(quantityPanel, BorderLayout.CENTER);
        panel.add(addButton, BorderLayout.EAST);
        
        return panel;
    }
    
    private void addToOrder(FoodItem item, int quantity) {
        double itemTotal = item.getPrice() * quantity;
        totalAmount += itemTotal;
        
        if (orderDetails.length() > 0) {
            orderDetails.append(", ");
        }
        orderDetails.append(item.getName()).append(" x").append(quantity);
        
        JOptionPane.showMessageDialog(this, "Added " + quantity + " " + item.getName() + " tokens to order");
    }
    
    private void proceedToPayment() {
        if (totalAmount == 0) {
            JOptionPane.showMessageDialog(this, "Please add items to your order");
            return;
        }
        
        new PaymentFrame(this, orderDetails.toString(), totalAmount, currentUser).setVisible(true);
    }
    
    public void resetOrder() {
        totalAmount = 0;
        orderDetails = new StringBuilder();
    }
}