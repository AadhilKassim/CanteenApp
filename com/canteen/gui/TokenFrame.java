package com.canteen.gui;

import com.canteen.model.Order;
import com.canteen.model.OrderItem;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;

public class TokenFrame extends JFrame implements Printable {
    private JPanel mainPanel;
    private Order order;

    private MainMenuFrame parentFrame;

    public TokenFrame(MainMenuFrame parent, Order order) {
        this.parentFrame = parent;
        this.order = order;
        setTitle("College Canteen Token Management System");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBackground(Color.WHITE);

        mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(40, 40, 40, 40));

        JLabel tokenLabel = new JLabel("Token", SwingConstants.CENTER);
        tokenLabel.setFont(new Font("Arial", Font.BOLD, 48));
        tokenLabel.setForeground(Color.BLACK);
        tokenLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(tokenLabel);
        mainPanel.add(Box.createVerticalStrut(30));

        JLabel tokenNum = new JLabel(String.valueOf(order.getTokenNumber()), SwingConstants.CENTER);
        tokenNum.setFont(new Font("Arial", Font.BOLD, 80));
        tokenNum.setForeground(Color.BLACK);
        tokenNum.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(tokenNum);
        mainPanel.add(Box.createVerticalStrut(40));

        String itemsText = order.getItems().size() == 1 ? 
            order.getItems().get(0).getFoodName() :
            order.getItems().size() + " items";
        JLabel foodName = new JLabel(itemsText, SwingConstants.CENTER);
        foodName.setFont(new Font("Arial", Font.PLAIN, 32));
        foodName.setForeground(Color.BLACK);
        foodName.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(foodName);
        mainPanel.add(Box.createVerticalStrut(30));

        JLabel amount = new JLabel("Total Amount: ₹" + String.format("%.0f", order.getTotalAmount()), SwingConstants.CENTER);
        amount.setFont(new Font("Arial", Font.PLAIN, 24));
        amount.setForeground(Color.BLACK);
        amount.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(amount);
        mainPanel.add(Box.createVerticalStrut(30));

        JLabel payment = new JLabel("Payment Method:", SwingConstants.CENTER);
        payment.setFont(new Font("Arial", Font.PLAIN, 24));
        payment.setForeground(Color.BLACK);
        payment.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(payment);
        
        JLabel paymentMethod = new JLabel(order.getPaymentMethod(), SwingConstants.CENTER);
        paymentMethod.setFont(new Font("Arial", Font.PLAIN, 24));
        paymentMethod.setForeground(Color.BLACK);
        paymentMethod.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(paymentMethod);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        
        JButton backButton = new JButton("Back to Menu");
        backButton.setFont(new Font("Arial", Font.PLAIN, 16));
        backButton.setPreferredSize(new Dimension(150, 40));
        backButton.setBackground(Color.LIGHT_GRAY);
        backButton.setForeground(Color.BLACK);
        backButton.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        backButton.addActionListener(e -> {
            setVisible(false);
            parentFrame.setVisible(true);
        });
        
        buttonPanel.add(backButton);
        
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public TokenFrame(Order order) {
        this(null, order);
    }

    public TokenFrame() {
        this(null, null);
    }

    private void printToken() {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setJobName("Canteen Token Print");
        job.setPrintable(this);

        if (job.printDialog()) {
            try {
                job.print();
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(this, "Print Failed: " + ex.getMessage());
            }
        }
    }

    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.translate(pf.getImageableX(), pf.getImageableY());
        mainPanel.printAll(g2);
        return PAGE_EXISTS;
    }
}