package com.canteen.gui;

import com.canteen.dao.OrderDAO;
import com.canteen.dao.DatabaseConnection;
import com.canteen.model.Order;
import com.canteen.model.OrderItem;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PaymentFrame extends JFrame {
    private JTable itemsTable;
    private JLabel totalLabel;
    private JComboBox<String> paymentMethodCombo;
    private String userEmail;
    private List<OrderItem> cartItems;
    private double totalAmount;
    private OrderDAO orderDAO;

    public PaymentFrame(String userEmail, List<OrderItem> cartItems) {
        this.userEmail = userEmail;
        this.cartItems = cartItems;
        this.orderDAO = new OrderDAO();
        setTitle("Payment - College Canteen");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        createItemsTable();
        createPaymentPanel();
        createButtonPanel();
        loadCartItems();
    }

    private void createItemsTable() {
        String[] columns = {"Item", "Quantity", "Price", "Subtotal"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        itemsTable = new JTable(model);
        itemsTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(itemsTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Order Summary"));
        add(scrollPane, BorderLayout.CENTER);
    }

    private void createPaymentPanel() {
        JPanel paymentPanel = new JPanel(new GridBagLayout());
        paymentPanel.setBorder(BorderFactory.createTitledBorder("Payment Details"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0;
        paymentPanel.add(new JLabel("Payment Method:"), gbc);

        paymentMethodCombo = new JComboBox<>(new String[]{"Credit Card", "Debit Card", "UPI", "Cash"});
        gbc.gridx = 1;
        paymentPanel.add(paymentMethodCombo, gbc);

        totalLabel = new JLabel("Total: ₹0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        paymentPanel.add(totalLabel, gbc);

        add(paymentPanel, BorderLayout.SOUTH);
    }

    private void createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        JButton backButton = new JButton("← Back to Menu");
        JButton payButton = new JButton("💳 Pay Now");
        JButton logoutButton = new JButton("Logout");

        backButton.addActionListener(e -> {
            dispose();
            new MenuSelectionFrame(userEmail).setVisible(true);
        });

        payButton.addActionListener(e -> {
            dispose();
            new PaymentMethodFrame(userEmail, cartItems, totalAmount).setVisible(true);
        });

        logoutButton.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        buttonPanel.add(backButton);
        buttonPanel.add(logoutButton);
        buttonPanel.add(payButton);
        add(buttonPanel, BorderLayout.NORTH);
    }

    private void loadCartItems() {
        DefaultTableModel model = (DefaultTableModel) itemsTable.getModel();
        totalAmount = 0;
        
        for (OrderItem item : cartItems) {
            model.addRow(new Object[]{
                item.getFoodName(),
                item.getQuantity(),
                "₹" + item.getPrice(),
                "₹" + item.getSubtotal()
            });
            totalAmount += item.getSubtotal();
        }
        
        totalLabel.setText("Total: ₹" + String.format("%.2f", totalAmount));
    }



    public PaymentFrame() {
        this("guest@college.edu", new java.util.ArrayList<>());
    }
}