package com.canteen.gui;

import com.canteen.dao.OrderDAO;
import com.canteen.dao.DatabaseConnection;
import com.canteen.model.Order;
import com.canteen.model.OrderItem;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PaymentMethodFrame extends JFrame {
    private String userEmail;
    private List<OrderItem> cartItems;
    private double totalAmount;
    private OrderDAO orderDAO;
    private ButtonGroup paymentGroup;

    private MainMenuFrame parentFrame;

    public PaymentMethodFrame(MainMenuFrame parent, String userEmail, List<OrderItem> cartItems, double totalAmount) {
        this.parentFrame = parent;
        this.userEmail = userEmail;
        this.cartItems = cartItems;
        this.totalAmount = totalAmount;
        this.orderDAO = new OrderDAO();
        
        setTitle("College Canteen Token Management System");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        
        JButton backButton = new JButton("← Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 16));
        backButton.addActionListener(e -> {
            setVisible(false);
            parentFrame.setVisible(true);
        });
        
        JLabel titleLabel = new JLabel("Payment", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(Color.BLACK);
        
        headerPanel.add(backButton, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        mainPanel.add(headerPanel);
        mainPanel.add(Box.createVerticalStrut(40));

        JLabel amountLabel = new JLabel("Total Amount: ₹ " + String.format("%.0f", totalAmount), SwingConstants.CENTER);
        amountLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        amountLabel.setForeground(Color.BLACK);
        amountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(amountLabel);
        mainPanel.add(Box.createVerticalStrut(60));

        JLabel methodLabel = new JLabel("Select Payment Method:", SwingConstants.CENTER);
        methodLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        methodLabel.setForeground(Color.BLACK);
        methodLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(methodLabel);
        mainPanel.add(Box.createVerticalStrut(30));

        paymentGroup = new ButtonGroup();
        String[] methods = {"Credit Card", "Debit Card", "Net Banking", "UPI"};
        
        for (int i = 0; i < methods.length; i++) {
            JRadioButton radioButton = new JRadioButton(methods[i]);
            radioButton.setFont(new Font("Arial", Font.PLAIN, 20));
            radioButton.setBackground(Color.WHITE);
            radioButton.setForeground(Color.BLACK);
            radioButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            if (i == 0) radioButton.setSelected(true);
            
            paymentGroup.add(radioButton);
            mainPanel.add(radioButton);
            mainPanel.add(Box.createVerticalStrut(15));
        }

        mainPanel.add(Box.createVerticalStrut(40));

        JButton payButton = new JButton("Make Payment");
        payButton.setFont(new Font("Arial", Font.PLAIN, 18));
        payButton.setPreferredSize(new Dimension(200, 50));
        payButton.setMaximumSize(new Dimension(200, 50));
        payButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        payButton.setBackground(Color.LIGHT_GRAY);
        payButton.setForeground(Color.BLACK);
        payButton.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        
        payButton.addActionListener(e -> processPayment());
        mainPanel.add(payButton);

        add(mainPanel);
    }

    private void processPayment() {
        String selectedMethod = "Credit Card";
        for (AbstractButton button : java.util.Collections.list(paymentGroup.getElements())) {
            if (button.isSelected()) {
                selectedMethod = button.getText();
                break;
            }
        }
        
        int orderId = DatabaseConnection.getNextOrderId();
        int tokenNumber = DatabaseConnection.getNextTokenNumber();
        
        Order order = new Order(orderId, userEmail, cartItems, totalAmount, selectedMethod, tokenNumber);
        orderDAO.saveOrder(order);
        
        setVisible(false);
        new TokenFrame(parentFrame, order).setVisible(true);
    }

    public PaymentMethodFrame(String userEmail, List<OrderItem> cartItems, double totalAmount) {
        this(null, userEmail, cartItems, totalAmount);
    }
}