package com.canteen.gui;

import com.canteen.model.User;
import javax.swing.*;
import java.awt.*;

public class MainMenuFrame extends JFrame {
    private User currentUser;
    
    public MainMenuFrame(User user) {
        this.currentUser = user;
        setTitle("College Canteen - Main Menu");
        setSize(400, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JLabel titleLabel = new JLabel("College Canteen", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(20));

        JLabel welcomeLabel = new JLabel("Welcome, " + currentUser.getName(), SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        welcomeLabel.setForeground(Color.BLACK);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(welcomeLabel);
        mainPanel.add(Box.createVerticalStrut(60));

        if (currentUser.isAdmin()) {
            createAdminButtons(mainPanel);
        } else {
            createStudentButtons(mainPanel);
        }

        add(mainPanel);
    }

    private void createAdminButtons(JPanel panel) {
        JButton manageMenuButton = createMenuButton("Manage Menu");
        manageMenuButton.addActionListener(e -> new MenuManagementFrame(this).setVisible(true));
        panel.add(manageMenuButton);
        panel.add(Box.createVerticalStrut(20));

        JButton viewTokensButton = createMenuButton("View All Tokens");
        viewTokensButton.addActionListener(e -> new TokenHistoryFrame(this).setVisible(true));
        panel.add(viewTokensButton);
        panel.add(Box.createVerticalStrut(20));

        JButton logoutButton = createMenuButton("Logout");
        logoutButton.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
        panel.add(logoutButton);
    }

    private void createStudentButtons(JPanel panel) {
        JButton orderButton = createMenuButton("Order Food");
        orderButton.addActionListener(e -> new MenuSelectionFrame(this, currentUser.getEmail()).setVisible(true));
        panel.add(orderButton);
        panel.add(Box.createVerticalStrut(20));

        JButton myTokensButton = createMenuButton("My Tokens");
        myTokensButton.addActionListener(e -> new MyTokensFrame(this, currentUser.getEmail()).setVisible(true));
        panel.add(myTokensButton);
        panel.add(Box.createVerticalStrut(20));

        JButton logoutButton = createMenuButton("Logout");
        logoutButton.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
        panel.add(logoutButton);
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setPreferredSize(new Dimension(300, 60));
        button.setMaximumSize(new Dimension(300, 60));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(Color.LIGHT_GRAY);
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        return button;
    }
}