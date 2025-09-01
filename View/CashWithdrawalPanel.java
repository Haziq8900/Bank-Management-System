package View;

import Backend.Account;

import javax.swing.*;
import javax.swing.border.EmptyBorder; // Required for EmptyBorder
import javax.swing.border.LineBorder; // Required for LineBorder
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class CashWithdrawalPanel extends JPanel {

    private ATMPanel parentATMPanel;
    private Account account;

    public CashWithdrawalPanel(ATMPanel parentATMPanel) {
        this.parentATMPanel = parentATMPanel;
        this.account = null;
        setLayout(new BorderLayout(0, 30)); // Add vertical gap for better spacing
        setBackground(new Color(230, 240, 250)); // Consistent light bluish background

        // --- Title Label ---
        JLabel titleLabel = new JLabel("Cash Withdrawal", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 38)); // Larger, bolder title for impact
        titleLabel.setForeground(new Color(46, 204, 113)); // Green color for the Withdrawal theme
        titleLabel.setBorder(new EmptyBorder(25, 0, 15, 0)); // More padding around title
        add(titleLabel, BorderLayout.NORTH);

        // --- Input Area (Central Focus resembling a transaction screen) ---
        JPanel inputContainerPanel = new JPanel(new GridBagLayout());
        inputContainerPanel.setBackground(new Color(255, 255, 255)); // White background for the input area
        inputContainerPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(144, 238, 144), 3, true), // Softer green border, rounded
            new EmptyBorder(40, 60, 40, 60) // Generous internal padding
        ));
        inputContainerPanel.setPreferredSize(new Dimension(550, 200)); // Adjusted size
        inputContainerPanel.setMaximumSize(new Dimension(550, 250));
        inputContainerPanel.setMinimumSize(new Dimension(750, 200));

        // Wrapper panel to visually center the inputContainerPanel within the BorderLayout.CENTER
        JPanel centerWrapperPanel = new JPanel(new GridBagLayout());
        centerWrapperPanel.setBackground(getBackground()); // Match main panel background
        centerWrapperPanel.add(inputContainerPanel);
        add(centerWrapperPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10); // Increased insets for more spacing around components
        gbc.anchor = GridBagConstraints.EAST; // Labels align right by default

        // --- Amount Field ---
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel amountLabel = new JLabel("Enter Amount");
        amountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        amountLabel.setForeground(new Color(80, 80, 80));
        inputContainerPanel.add(amountLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST; // Text field aligns left
        gbc.fill = GridBagConstraints.HORIZONTAL; // Make the component fill horizontally
        gbc.weightx = 1.0; // Give all extra horizontal space to this column
        JTextField amountField = new JTextField();
        amountField.setFont(new Font("Segoe UI", Font.PLAIN, 20)); // Consistent font size for input
        amountField.setForeground(new Color(80, 80, 80)); // Ensure text color is black
        amountField.setPreferredSize(new Dimension(amountField.getPreferredSize().width, 30)); // Fixed height
        amountField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(180, 180, 180), 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        inputContainerPanel.add(amountField, gbc);

        // Reset weights for the button row
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;

        // --- Withdraw Button ---
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 2; // Span across both columns
        gbc.anchor = GridBagConstraints.CENTER; // Center the button horizontally
        gbc.insets = new Insets(30, 10, 10, 10); // More top padding for the button
        JButton withdrawButton = createStyledButton("Withdraw", new Color(46, 204, 113)); // Themed button
        inputContainerPanel.add(withdrawButton, gbc);


        // --- Action Listener for Withdraw Button ---
        withdrawButton.addActionListener(e -> {
            String amountText = amountField.getText().trim();
            if (amountText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter an amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (account == null) {
                JOptionPane.showMessageDialog(this, "No account selected. Please enter your account details first.", "Account Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double amount = Double.parseDouble(amountText);
                if (amount < 1) {
                    JOptionPane.showMessageDialog(this, "Please enter a positive amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    account.withdraw(amount);
                    JOptionPane.showMessageDialog(this,
                            String.format("Successfully withdrew $%,.2f.%nYour transaction is being processed.", amount),
                            "Withdrawal Successful", JOptionPane.INFORMATION_MESSAGE);

                    amountField.setText(""); // Clear field after successful withdrawal

                    // Optionally, navigate back or update balance display
                    // parentATMPanel.showATMMainMenu();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Withdrawal Failed", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount. Please enter numbers only (e.g., 100.00).", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /**
     * Sets the account for this panel.
     * @param account The account to use for withdrawals.
     */
    public void setAccount(Backend.Account account) {
        this.account = account;
    }

    /**
     * Helper method to create consistently styled buttons with enhanced hover effects.
     */
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(220, 40)); // Consistent button size
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
