package com.canteen.gui;

import com.canteen.dao.MenuDAO;
import com.canteen.model.FoodItem;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MenuManagementFrame extends JFrame {
    private JTable menuTable;
    private DefaultTableModel tableModel;
    private MenuDAO menuDAO;
    private MainMenuFrame parentFrame;

    public MenuManagementFrame(MainMenuFrame parent) {
        this.parentFrame = parent;
        setTitle("TOC H Menu - Admin Panel");
        setSize(650, 400);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        menuDAO = new MenuDAO();

        Color bgColor = Color.WHITE;
        Color headerBg = Color.LIGHT_GRAY;
        Color headerText = Color.BLACK;
        Color btnBg = Color.LIGHT_GRAY;
        Color btnText = Color.BLACK;

        getContentPane().setBackground(bgColor);

        String[] columnNames = {"ID", "Name", "Price (₹)"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        menuTable = new JTable(tableModel);
        menuTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        menuTable.getTableHeader().setReorderingAllowed(false);
        menuTable.getTableHeader().setBackground(headerBg);
        menuTable.getTableHeader().setForeground(headerText);
        menuTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        menuTable.setRowHeight(28);

        JScrollPane scrollPane = new JScrollPane(menuTable);
        scrollPane.getViewport().setBackground(bgColor);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(bgColor);

        JButton addButton = new JButton("➕ Add Item");
        JButton updateButton = new JButton("✏ Update Item");
        JButton deleteButton = new JButton("🗑 Delete Item");
        JButton viewTokensButton = new JButton("🎫 View Tokens");
        JButton backButton = new JButton("← Back");
        JButton logoutButton = new JButton("🚪 Logout");

        Font btnFont = new Font("SansSerif", Font.BOLD, 14);
        JButton[] buttons = {backButton, addButton, updateButton, deleteButton, viewTokensButton, logoutButton};
        for (JButton btn : buttons) {
            btn.setBackground(btnBg);
            btn.setForeground(btnText);
            btn.setFont(btnFont);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
            buttonPanel.add(btn);
        }

        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addFoodItem());
        updateButton.addActionListener(e -> updateFoodItem());
        deleteButton.addActionListener(e -> deleteFoodItem());
        backButton.addActionListener(e -> {
            setVisible(false);
            parentFrame.setVisible(true);
        });
        viewTokensButton.addActionListener(e -> {
            setVisible(false);
            new TokenHistoryFrame(parentFrame).setVisible(true);
        });
        logoutButton.addActionListener(e -> {
            dispose();
            parentFrame.dispose();
            new LoginFrame().setVisible(true);
        });

        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<FoodItem> items = menuDAO.getAllMenuItems();
        for (FoodItem item : items) {
            tableModel.addRow(new Object[]{item.id, item.name, item.price});
        }
    }

    private void addFoodItem() {
        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();
        Object[] message = {
            new JLabel("Food Name:"), nameField,
            new JLabel("Price:"), priceField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add Item", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText().trim();
                double price = Double.parseDouble(priceField.getText().trim());
                if (name.isEmpty()) throw new Exception("Name cannot be empty");

                FoodItem newItem = new FoodItem(0, name, price);
                menuDAO.saveMenuItem(newItem);
                refreshTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateFoodItem() {
        int row = menuTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select an item to update.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (Integer) tableModel.getValueAt(row, 0);
        String currentName = (String) tableModel.getValueAt(row, 1);
        double currentPrice = (Double) tableModel.getValueAt(row, 2);

        JTextField nameField = new JTextField(currentName);
        JTextField priceField = new JTextField(String.valueOf(currentPrice));
        Object[] message = {
            new JLabel("New Name:"), nameField,
            new JLabel("New Price:"), priceField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Update Item", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText().trim();
                double price = Double.parseDouble(priceField.getText().trim());
                if (name.isEmpty()) throw new Exception("Name cannot be empty");

                FoodItem updatedItem = new FoodItem(id, name, price);
                menuDAO.saveMenuItem(updatedItem);
                refreshTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteFoodItem() {
        int row = menuTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select an item to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (Integer) tableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Delete this item?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            menuDAO.deleteMenuItem(id);
            refreshTable();
        }
    }

    public MenuManagementFrame() {
        this(null);
    }
}