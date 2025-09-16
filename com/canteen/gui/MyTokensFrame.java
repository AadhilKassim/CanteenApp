package com.canteen.gui;

import com.canteen.dao.OrderDAO;
import com.canteen.model.Order;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MyTokensFrame extends JFrame {
    private MainMenuFrame parentFrame;
    private String userEmail;
    private JTable tokenTable;
    private DefaultTableModel tableModel;
    private OrderDAO orderDAO;

    public MyTokensFrame(MainMenuFrame parent, String userEmail) {
        this.parentFrame = parent;
        this.userEmail = userEmail;
        this.orderDAO = new OrderDAO();
        
        setTitle("My Tokens");
        setSize(400, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        
        JButton backButton = new JButton("← Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 16));
        backButton.addActionListener(e -> {
            setVisible(false);
            parentFrame.setVisible(true);
        });
        
        JLabel titleLabel = new JLabel("My Tokens", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        
        headerPanel.add(backButton, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        String[] columns = {"Token #", "Items", "Amount", "Date"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tokenTable = new JTable(tableModel);
        tokenTable.setRowHeight(40);
        tokenTable.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(tokenTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
        loadMyTokens();
    }

    private void loadMyTokens() {
        tableModel.setRowCount(0);
        List<Order> orders = orderDAO.getAllOrders();
        
        for (Order order : orders) {
            if (order.getUserEmail().equals(userEmail)) {
                String itemsText = order.getItems().size() == 1 ? 
                    order.getItems().get(0).getFoodName() :
                    order.getItems().size() + " items";
                
                tableModel.addRow(new Object[]{
                    order.getTokenNumber(),
                    itemsText,
                    "₹" + String.format("%.0f", order.getTotalAmount()),
                    order.getOrderTime().toString().substring(0, 16)
                });
            }
        }
    }
}