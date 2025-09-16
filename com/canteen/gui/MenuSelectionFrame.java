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
    private MainMenuFrame parentFrame;
    private JLabel cartLabel;

    public MenuSelectionFrame(MainMenuFrame parent, String userEmail) {
        this.parentFrame = parent;
        this.userEmail = userEmail;
        this.menuDAO = new MenuDAO();
        this.cart = new ArrayList<>();
        
        setTitle("College Canteen Token Management System");
        setSize(400, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        createMainPanel();
        loadMenuItems();
    }

    private void createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        
        JButton backButton = new JButton("← Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 16));
        backButton.addActionListener(e -> {
            setVisible(false);
            parentFrame.setVisible(true);
        });
        
        JLabel titleLabel = new JLabel("Select Items", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        
        headerPanel.add(backButton, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        mainPanel.add(headerPanel);
        mainPanel.add(Box.createVerticalStrut(30));

        List<FoodItem> items = menuDAO.getAllMenuItems();
        for (FoodItem item : items) {
            JPanel itemPanel = createItemPanel(item);
            mainPanel.add(itemPanel);
            mainPanel.add(Box.createVerticalStrut(15));
        }

        mainPanel.add(Box.createVerticalStrut(20));
        
        cartLabel = new JLabel("Selected Items: 0 | Total: ₹0", SwingConstants.CENTER);
        cartLabel.setFont(new Font("Arial", Font.BOLD, 16));
        cartLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(cartLabel);
        mainPanel.add(Box.createVerticalStrut(20));

        JButton buyButton = new JButton("Proceed to Payment");
        buyButton.setFont(new Font("Arial", Font.PLAIN, 18));
        buyButton.setPreferredSize(new Dimension(300, 50));
        buyButton.setMaximumSize(new Dimension(300, 50));
        buyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buyButton.setBackground(Color.LIGHT_GRAY);
        buyButton.setForeground(Color.BLACK);
        buyButton.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        
        buyButton.addActionListener(e -> {
            if (cart.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select items");
                return;
            }
            double total = cart.stream().mapToDouble(OrderItem::getSubtotal).sum();
            setVisible(false);
            new PaymentMethodFrame(parentFrame, userEmail, cart, total).setVisible(true);
        });
        
        mainPanel.add(buyButton);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createItemPanel(FoodItem item) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(350, 80));
        panel.setPreferredSize(new Dimension(350, 80));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel nameLabel = new JLabel(item.name);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        JLabel priceLabel = new JLabel("₹" + String.format("%.0f", item.price));
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        priceLabel.setForeground(Color.GRAY);

        infoPanel.add(nameLabel);
        infoPanel.add(priceLabel);
        panel.add(infoPanel, BorderLayout.CENTER);

        JButton addButton = new JButton("+");
        addButton.setFont(new Font("Arial", Font.BOLD, 20));
        addButton.setPreferredSize(new Dimension(50, 50));
        addButton.setBackground(Color.white);
        addButton.setForeground(Color.black);
        addButton.setBorder(BorderFactory.createEmptyBorder());
        
        addButton.addActionListener(e -> {
            OrderItem orderItem = new OrderItem(item.id, item.name, 1, item.price);
            cart.add(orderItem);
            updateCartDisplay();
        });
        
        panel.add(addButton, BorderLayout.EAST);
        return panel;
    }
    
    private void updateCartDisplay() {
        int totalItems = cart.size();
        double totalAmount = cart.stream().mapToDouble(OrderItem::getSubtotal).sum();
        cartLabel.setText("Selected Items: " + totalItems + " | Total: ₹" + String.format("%.0f", totalAmount));
    }

    private void loadMenuItems() {
        // Items are loaded in createMainPanel
    }

    public MenuSelectionFrame(String userEmail) {
        this(null, userEmail);
    }
}