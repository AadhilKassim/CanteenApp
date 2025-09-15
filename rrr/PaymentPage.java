package rrr;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class PaymentPage extends JFrame {

    private JTable itemsTable;
    private JLabel totalLabel;
    private JButton payButton;

    public PaymentPage() {
        setTitle("Payment Page");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set overall background to white
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout(10, 10));

        String[] columns = {"Item", "Quantity", "Price", "Subtotal"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        itemsTable = new JTable(model);
        itemsTable.setFillsViewportHeight(true);

        // Table styling
        itemsTable.setBackground(Color.WHITE);
        itemsTable.setForeground(Color.BLACK);
        itemsTable.setGridColor(Color.LIGHT_GRAY);
        itemsTable.setSelectionBackground(new Color(220, 220, 220)); // light grey selection
        itemsTable.setSelectionForeground(Color.BLACK);
        itemsTable.setRowHeight(25);
        itemsTable.setFont(new Font("Arial", Font.PLAIN, 14));

        // Center align Quantity, Price, Subtotal columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        itemsTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        itemsTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        itemsTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

        // Header styling
        JTableHeader header = itemsTable.getTableHeader();
        header.setBackground(Color.LIGHT_GRAY);
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(itemsTable);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        // Total label styling
        totalLabel = new JLabel("Total: ₹0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalLabel.setForeground(Color.BLACK);
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        // Pay button styling
        payButton = new JButton("Pay");
        payButton.setFont(new Font("Arial", Font.BOLD, 14));
        payButton.setBackground(Color.BLACK);
        payButton.setForeground(Color.WHITE);
        payButton.setFocusPainted(false);
        payButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        payButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Button hover effect
        payButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                payButton.setBackground(Color.DARK_GRAY);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                payButton.setBackground(Color.BLACK);
            }
        });

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(totalLabel, BorderLayout.CENTER);
        bottomPanel.add(payButton, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PaymentPage paymentPage = new PaymentPage();

            // Add some sample data for demonstration
            DefaultTableModel model = (DefaultTableModel) paymentPage.itemsTable.getModel();
            model.addRow(new Object[]{"Apple", 2, "₹50.00", "₹100.00"});
            model.addRow(new Object[]{"Banana", 5, "₹10.00", "₹50.00"});
            model.addRow(new Object[]{"Orange", 3, "₹30.00", "₹90.00"});
            paymentPage.totalLabel.setText("Total: ₹240.00");

            paymentPage.setVisible(true);
        });
    }
}