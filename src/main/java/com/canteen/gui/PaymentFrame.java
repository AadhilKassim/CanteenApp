package com.canteen.gui;

import com.canteen.dao.TokenDAO;
import com.canteen.model.Token;
import com.canteen.model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class PaymentFrame extends JFrame {
    private BookingFrame parentFrame;
    private String orderDetails;
    private double totalAmount;
    private User currentUser;
    private TokenDAO tokenDAO;
    private ButtonGroup paymentGroup;
    
    public PaymentFrame(BookingFrame parent, String orderDetails, double totalAmount, User user) {
        this.parentFrame = parent;
        this.orderDetails = orderDetails;
        this.totalAmount = totalAmount;
        this.currentUser = user;
        this.tokenDAO = new TokenDAO();
        initializeComponents();
    }
    
    private void initializeComponents() {
        setTitle("Payment");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(parentFrame);
        setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Title
        JLabel titleLabel = new JLabel("Payment");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(titleLabel, gbc);
        
        // Total amount
        JLabel amountLabel = new JLabel("Total Amount: ₹" + (int)totalAmount);
        amountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridy = 1;
        add(amountLabel, gbc);
        
        // Payment method selection
        JLabel methodLabel = new JLabel("Select Payment Method:");
        gbc.gridy = 2;
        add(methodLabel, gbc);
        
        paymentGroup = new ButtonGroup();
        JRadioButton creditCard = new JRadioButton("Credit Card", true);
        JRadioButton debitCard = new JRadioButton("Debit Card");
        JRadioButton netBanking = new JRadioButton("Net Banking");
        JRadioButton upi = new JRadioButton("UPI");
        
        paymentGroup.add(creditCard);
        paymentGroup.add(debitCard);
        paymentGroup.add(netBanking);
        paymentGroup.add(upi);
        
        gbc.gridwidth = 1;
        gbc.gridy = 3;
        add(creditCard, gbc);
        gbc.gridy = 4;
        add(debitCard, gbc);
        gbc.gridy = 5;
        add(netBanking, gbc);
        gbc.gridy = 6;
        add(upi, gbc);
        
        // Make payment button
        JButton payButton = new JButton("Make Payment");
        gbc.gridwidth = 2;
        gbc.gridy = 7;
        add(payButton, gbc);
        
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processPayment();
            }
        });
    }
    
    private void processPayment() {
        String selectedPayment = "";
        for (AbstractButton button : java.util.Collections.list(paymentGroup.getElements())) {
            if (button.isSelected()) {
                selectedPayment = button.getText();
                break;
            }
        }
        
        // Generate token
        String tokenNumber = tokenDAO.generateTokenNumber();
        Token token = new Token(
            tokenNumber,
            currentUser.getEmail(),
            orderDetails,
            totalAmount,
            selectedPayment,
            LocalDateTime.now(),
            "PAID"
        );
        
        if (tokenDAO.saveToken(token)) {
            dispose();
            new TokenFrame(token).setVisible(true);
            parentFrame.resetOrder();
        } else {
            JOptionPane.showMessageDialog(this, "Payment failed. Please try again.");
        }
    }
}