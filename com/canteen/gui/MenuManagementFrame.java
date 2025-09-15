package com.canteen.gui;

import com.canteen.model.FoodItem;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;

public class MenuManagementFrame extends JFrame {
    private JTable menuTable;
    private DefaultTableModel tableModel;
    private static Map<Integer, FoodItem> menu = new HashMap<>();
    private static int idCounter = 1;

    public MenuManagementFrame() {
        setTitle("TOC H Menu - Admin Panel");
        setSize(650, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        preloadMenu();

        Color bgColor = Color.WHITE;
        Color headerBg = new Color(45, 45, 45);
        Color headerText = Color.WHITE;
        Color btnBg = Color.BLACK;
        Color btnText = Color.WHITE;

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
        JButton logoutButton = new JButton("🚪 Logout");

        Font btnFont = new Font("SansSerif", Font.BOLD, 14);
        JButton[] buttons = {addButton, updateButton, deleteButton, logoutButton};
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
        logoutButton.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        Map<Integer, FoodItem> sortedMenu = new TreeMap<>(menu);
        for (FoodItem item : sortedMenu.values()) {
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

                menu.put(idCounter, new FoodItem(idCounter, name, price));
                idCounter++;
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
        FoodItem item = menu.get(id);

        JTextField nameField = new JTextField(item.name);
        JTextField priceField = new JTextField(String.valueOf(item.price));
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

                menu.put(id, new FoodItem(id, name, price));
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
            menu.remove(id);
            refreshTable();
        }
    }

    private void preloadMenu() {
        if (menu.isEmpty()) {
            menu.put(idCounter, new FoodItem(idCounter++, "Half Biriyani", 60));
            menu.put(idCounter, new FoodItem(idCounter++, "Full Biriyani", 120));
            menu.put(idCounter, new FoodItem(idCounter++, "Meals", 40));
            menu.put(idCounter, new FoodItem(idCounter++, "Chicken Fry", 60));
        }
    }
}