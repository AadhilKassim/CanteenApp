package rrr;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;

public class CanteenTokenUI extends JFrame implements Printable {

    private JPanel mainPanel; // We'll print this panel

    public CanteenTokenUI() {
        setTitle("College Canteen Token");
        setSize(350, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main panel with white background
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Title banner
        JLabel title = new JLabel("CANTEEN TOKEN", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setOpaque(true);
        title.setBackground(new Color(240, 240, 240));
        title.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
        mainPanel.add(title, BorderLayout.NORTH);

        // Center panel for token number
        JPanel tokenPanel = new JPanel();
        tokenPanel.setBackground(Color.WHITE);
        tokenPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel tokenNum = new JLabel("45 / 50");
        tokenNum.setFont(new Font("SansSerif", Font.BOLD, 32));
        tokenNum.setForeground(Color.BLACK);
        tokenNum.setBorder(new LineBorder(Color.GRAY, 3, true)); // circle effect
        tokenNum.setHorizontalAlignment(SwingConstants.CENTER);
        tokenNum.setPreferredSize(new Dimension(150, 80));

        tokenPanel.add(tokenNum);
        mainPanel.add(tokenPanel, BorderLayout.CENTER);

        // Info section at bottom
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setLayout(new GridLayout(3, 1, 5, 5));

        JLabel foodName = new JLabel("🍴  Chicken Biryani", SwingConstants.CENTER);
        foodName.setFont(new Font("SansSerif", Font.PLAIN, 18));

        JLabel amount = new JLabel("💰  Total: ₹120", SwingConstants.CENTER);
        amount.setFont(new Font("SansSerif", Font.PLAIN, 16));

        JLabel payment = new JLabel("💳  Paid by Credit Card", SwingConstants.CENTER);
        payment.setFont(new Font("SansSerif", Font.PLAIN, 16));

        infoPanel.add(foodName);
        infoPanel.add(amount);
        infoPanel.add(payment);

        mainPanel.add(infoPanel, BorderLayout.SOUTH);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton printButton = new JButton("🖨 Print Token");
        printButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printToken();
            }
        });
        buttonPanel.add(printButton);

        // Add everything to frame
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Method to trigger printing
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

    // Define how to print (print the mainPanel)
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CanteenTokenUI().setVisible(true);
        });
    }
}