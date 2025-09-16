package com.canteen.gui;

import com.canteen.dao.OrderDAO;
import com.canteen.model.Order;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TokenHistoryFrame extends JFrame {
    private JTable tokenTable;
    private DefaultTableModel tableModel;
    private OrderDAO orderDAO;
    private MainMenuFrame parentFrame;

    public TokenHistoryFrame(MainMenuFrame parent) {
        this.parentFrame = parent;
        orderDAO = new OrderDAO();
        setTitle("Token History - College Canteen");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        String[] columns = {"Token #", "User", "Items", "Total", "Payment", "Date", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tokenTable = new JTable(tableModel);
        tokenTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(tokenTable);

        JPanel buttonPanel = new JPanel();
        JButton refreshButton = new JButton("Refresh");
        JButton backButton = new JButton("← Back");

        refreshButton.addActionListener(e -> loadTokenHistory());
        backButton.addActionListener(e -> {
            setVisible(false);
            parentFrame.setVisible(true);
        });

        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loadTokenHistory();
    }

    public TokenHistoryFrame() {
        this(null);
    }

    private void loadTokenHistory() {
        tableModel.setRowCount(0);
        List<Order> orders = orderDAO.getAllOrders();
        
        for (Order order : orders) {
            String itemsText = order.getItems().size() == 1 ? 
                order.getItems().get(0).getFoodName() :
                order.getItems().size() + " items";
            
            tableModel.addRow(new Object[]{
                order.getTokenNumber(),
                order.getUserEmail().split("@")[0],
                itemsText,
                "₹" + String.format("%.2f", order.getTotalAmount()),
                order.getPaymentMethod(),
                order.getOrderTime().toString().substring(0, 19),
                order.getStatus()
            });
        }
    }
}