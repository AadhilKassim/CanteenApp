package com.canteen.gui;

import com.canteen.model.Token;
import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class TokenFrame extends JFrame {
    private Token token;
    
    public TokenFrame(Token token) {
        this.token = token;
        initializeComponents();
    }
    
    private void initializeComponents() {
        setTitle("College Canteen Token Management System");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Title
        JLabel titleLabel = new JLabel("Token");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0; gbc.gridy = 0;
        add(titleLabel, gbc);
        
        // Token number
        JLabel tokenLabel = new JLabel(token.getTokenNumber());
        tokenLabel.setFont(new Font("Arial", Font.BOLD, 48));
        gbc.gridy = 1;
        add(tokenLabel, gbc);
        
        // Food details
        JLabel foodLabel = new JLabel(token.getFoodDetails());
        foodLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridy = 2;
        add(foodLabel, gbc);
        
        // Total amount
        JLabel amountLabel = new JLabel("Total Amount: ₹" + (int)token.getTotalAmount());
        amountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridy = 3;
        add(amountLabel, gbc);
        
        // Payment method
        JLabel paymentLabel = new JLabel("Payment Method: " + token.getPaymentMethod());
        gbc.gridy = 4;
        add(paymentLabel, gbc);
        
        // Order time
        JLabel timeLabel = new JLabel("Order Time: " + token.getOrderTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        gbc.gridy = 5;
        add(timeLabel, gbc);
    }
}