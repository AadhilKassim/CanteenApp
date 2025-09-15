package com.canteen.gui;

import com.canteen.dao.MenuDAO;
import com.canteen.model.FoodItem;
import com.canteen.model.OrderItem;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

public class MenuSelectionFrame extends JFrame {
    private JTable menuTable;
    private JTable cartTable;
    private DefaultTableModel menuModel;
    private DefaultTableModel cartModel;
    private JLabel totalLabel;
    private MenuDAO menuDAO;
    private List<OrderItem> cart;
    private String userEmail;

    public MenuSelectionFrame(String userEmail) {
        this.userEmail = userEmail;
        this.menuDAO = new MenuDAO();
        this.cart = new ArrayList<>();
        
        setTitle("Select Items - College Canteen");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        createMenuPanel();
        createCartPanel();
        createButtonPanel();
        loadMenuItems();
    }

    private void createMenuPanel() {
        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBorder(BorderFactory.createTitledBorder("Menu"));

        String[] menuColumns = {"ID", "Item", "Price"};
        menuModel = new DefaultTableModel(menuColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        menuTable = new JTable(menuModel);
        menuTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane menuScroll = new JScrollPane(menuTable);

        JPanel addPanel = new JPanel();
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        JButton addButton = new JButton("Add to Cart");

        addButton.addActionListener(e -> {
            int row = menuTable.getSelectedRow();
            if (row != -1) {
                int id = (Integer) menuModel.getValueAt(row, 0);
                String name = (String) menuModel.getValueAt(row, 1);
                double price = Double.parseDouble(menuModel.getValueAt(row, 2).toString().replace("₹", ""));
                int quantity = (Integer) quantitySpinner.getValue();

                OrderItem item = new OrderItem(id, name, quantity, price);
                cart.add(item);
                updateCartTable();
                quantitySpinner.setValue(1);
            }
        });

        addPanel.add(new JLabel("Quantity:"));
        addPanel.add(quantitySpinner);
        addPanel.add(addButton);

        menuPanel.add(menuScroll, BorderLayout.CENTER);
        menuPanel.add(addPanel, BorderLayout.SOUTH);
        add(menuPanel, BorderLayout.WEST);
    }

    private void createCartPanel() {
        JPanel cartPanel = new JPanel(new BorderLayout());
        cartPanel.setBorder(BorderFactory.createTitledBorder("Cart"));

        String[] cartColumns = {"Item", "Qty", "Price", "Subtotal"};
        cartModel = new DefaultTableModel(cartColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        cartTable = new JTable(cartModel);
        JScrollPane cartScroll = new JScrollPane(cartTable);

        totalLabel = new JLabel("Total: ₹0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JButton removeButton = new JButton("Remove Selected");
        removeButton.addActionListener(e -> {
            int row = cartTable.getSelectedRow();
            if (row != -1) {
                cart.remove(row);
                updateCartTable();
            }
        });

        JPanel cartBottomPanel = new JPanel(new BorderLayout());
        cartBottomPanel.add(removeButton, BorderLayout.WEST);
        cartBottomPanel.add(totalLabel, BorderLayout.EAST);

        cartPanel.add(cartScroll, BorderLayout.CENTER);
        cartPanel.add(cartBottomPanel, BorderLayout.SOUTH);
        add(cartPanel, BorderLayout.EAST);
    }

    private void createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        JButton proceedButton = new JButton("Proceed to Payment");
        JButton logoutButton = new JButton("Logout");

        proceedButton.addActionListener(e -> {
            if (cart.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please add items to cart");
                return;
            }
            dispose();
            new PaymentFrame(userEmail, cart).setVisible(true);
        });

        logoutButton.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        buttonPanel.add(logoutButton);
        buttonPanel.add(proceedButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadMenuItems() {
        List<FoodItem> items = menuDAO.getAllMenuItems();
        for (FoodItem item : items) {
            menuModel.addRow(new Object[]{item.id, item.name, "₹" + item.price});
        }
    }

    private void updateCartTable() {
        cartModel.setRowCount(0);
        double total = 0;
        for (OrderItem item : cart) {
            cartModel.addRow(new Object[]{
                item.getFoodName(),
                item.getQuantity(),
                "₹" + item.getPrice(),
                "₹" + item.getSubtotal()
            });
            total += item.getSubtotal();
        }
        totalLabel.setText("Total: ₹" + String.format("%.2f", total));
    }
}