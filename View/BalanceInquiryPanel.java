package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class BalanceInquiryPanel extends JPanel {

    private ATMPanel parentATMPanel;
    private JLabel balanceValueLabel; // Changed name to clearly indicate it holds the value
    private JLabel balanceInfoPrefixLabel; // New label for "Your current balance:"
    private JPanel balanceDisplayContainer;
    private Timer loadingTimer;

    public BalanceInquiryPanel(ATMPanel parentATMPanel) {
        this.parentATMPanel = parentATMPanel;
        setLayout(new BorderLayout(0, 30));
        setBackground(new Color(230, 240, 250));

        // --- Title Label ---
        JLabel titleLabel = new JLabel("Balance Inquiry", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 38));
        titleLabel.setForeground(new Color(52, 152, 219));
        titleLabel.setBorder(new EmptyBorder(25, 0, 15, 0));
        add(titleLabel, BorderLayout.NORTH);

        // --- Balance Display Area (Central Focus resembling a digital screen) ---
        balanceDisplayContainer = new JPanel(new GridBagLayout());
        balanceDisplayContainer.setBackground(new Color(255, 255, 255));
        balanceDisplayContainer.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(150, 200, 250), 3, true),
            new EmptyBorder(40, 60, 40, 60)
        ));
        balanceDisplayContainer.setPreferredSize(new Dimension(550, 200));
        balanceDisplayContainer.setMaximumSize(new Dimension(550, 200));
        balanceDisplayContainer.setMinimumSize(new Dimension(450, 150));

        JPanel centerWrapperPanel = new JPanel(new GridBagLayout());
        centerWrapperPanel.setBackground(getBackground());
        centerWrapperPanel.add(balanceDisplayContainer);
        add(centerWrapperPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 15, 0); // Space below info prefix label

        // NEW: Separate label for the "Your current balance:" prefix
        balanceInfoPrefixLabel = new JLabel("Your Current Available Balance:");
        balanceInfoPrefixLabel.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        balanceInfoPrefixLabel.setForeground(new Color(80, 80, 80));
        balanceDisplayContainer.add(balanceInfoPrefixLabel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0); // No extra space below balance value
        
        // NEW: Dedicated label for the balance value, now without the prefix
        balanceValueLabel = new JLabel("Fetching balance...", SwingConstants.CENTER);
        // Adjusted font size to fit better, keeping it large and impactful
        balanceValueLabel.setFont(new Font("Consolas", Font.BOLD, 42)); // Reduced from 48 to 42
        balanceValueLabel.setForeground(new Color(40, 120, 180));
        balanceDisplayContainer.add(balanceValueLabel, gbc);


        // --- Simulate Balance Fetching Logic ---
        loadingTimer = new Timer(1800, e -> {
            updateBalanceDisplay(generateRandomBalance());
            balanceDisplayContainer.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(76, 175, 80), 3, true),
                new EmptyBorder(40, 60, 40, 60)
            ));
            ((Timer)e.getSource()).stop();
        });
        loadingTimer.setRepeats(false);

        addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent event) {
                // Reset state and start fetching simulation when panel is shown
                balanceInfoPrefixLabel.setVisible(false); // Hide prefix during fetching
                balanceValueLabel.setText("Fetching balance...");
                balanceValueLabel.setFont(new Font("Consolas", Font.BOLD, 42)); // Ensure font size is consistent
                balanceValueLabel.setForeground(new Color(40, 120, 180));
                balanceDisplayContainer.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(150, 200, 250), 3, true),
                    new EmptyBorder(40, 60, 40, 60)
                ));
                loadingTimer.restart(); // Use restart for robust behavior
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent event) {
                if (loadingTimer != null && loadingTimer.isRunning()) {
                    loadingTimer.stop();
                }
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent event) { /* Not used */ }
        });
    }

    /**
     * Updates the text and color of the balance display label.
     * @param balance The current balance to display.
     */
    public void updateBalanceDisplay(double balance) {
        balanceInfoPrefixLabel.setVisible(true); // Show prefix once balance is loaded
        balanceValueLabel.setText("$" + String.format("%,.2f", balance)); // Only show the value here
        balanceValueLabel.setFont(new Font("Consolas", Font.BOLD, 42)); // Keep this large for the value
        balanceValueLabel.setForeground(new Color(76, 175, 80)); // Green for successful display
    }

    /**
     * Helper method to simulate fetching a random balance.
     */
    private double generateRandomBalance() {
        Random rand = new Random();
        return 100.00 + (20000.00 - 100.00) * rand.nextDouble();
    }

    /**
     * Helper method to create consistently styled buttons with enhanced hover effects.
     */
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 55));
        button.setFont(new Font("Segoe UI", Font.BOLD, 19));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker(), 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(bgColor.brighter());
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(bgColor);
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        return button;
    }
}