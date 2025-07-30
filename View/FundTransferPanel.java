package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder; // Required for EmptyBorder
import javax.swing.border.LineBorder; // Required for LineBorder
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FundTransferPanel extends JPanel {
    private ATMPanel parentATMPanel;

    public FundTransferPanel(ATMPanel parentATMPanel) {
        this.parentATMPanel = parentATMPanel;
        setLayout(new BorderLayout(0, 30)); // Add vertical gap for better spacing
        setBackground(new Color(230, 240, 250)); // Consistent light bluish background

        // --- Title Label ---
        JLabel titleLabel = new JLabel("Fund Transfer", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 38)); // Larger, bolder title
        titleLabel.setForeground(new Color(230, 126, 34)); // Orange color for the Fund Transfer theme
        titleLabel.setBorder(new EmptyBorder(25, 0, 15, 0)); // More padding around title
        add(titleLabel, BorderLayout.NORTH);

        // --- Input Area (Central Focus resembling a transaction screen) ---
        // This panel will contain all input fields and the transfer button
        JPanel inputContainerPanel = new JPanel(new GridBagLayout());
        inputContainerPanel.setBackground(new Color(255, 255, 255)); // White background for the input area
        inputContainerPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(255, 179, 102), 3, true), // Softer orange border, rounded
            new EmptyBorder(40, 60, 40, 60) // Generous internal padding
        ));
        inputContainerPanel.setPreferredSize(new Dimension(650, 350)); // Adjusted size for more fields
        inputContainerPanel.setMaximumSize(new Dimension(650, 350));
        inputContainerPanel.setMinimumSize(new Dimension(550, 300));

        // Wrapper panel to visually center the inputContainerPanel within the BorderLayout.CENTER
        JPanel centerWrapperPanel = new JPanel(new GridBagLayout());
        centerWrapperPanel.setBackground(getBackground()); // Match main panel background
        centerWrapperPanel.add(inputContainerPanel);
        add(centerWrapperPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10); // Increased insets for more spacing around components
        gbc.anchor = GridBagConstraints.EAST; // Labels align right by default

        // --- From Account Field ---
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel fromAccountLabel = new JLabel("From Account Number:");
        fromAccountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        fromAccountLabel.setForeground(new Color(80, 80, 80));
        inputContainerPanel.add(fromAccountLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST; // Text field aligns left
        gbc.fill = GridBagConstraints.HORIZONTAL; // Make the component fill horizontally
        gbc.weightx = 1.0; // Give all extra horizontal space to this column
        JTextField fromAccountField = new JTextField(15);
        fromAccountField.setFont(new Font("Consolas", Font.PLAIN, 12));
        fromAccountField.setForeground(Color.BLACK); // Ensure text color is black
        fromAccountField.setPreferredSize(new Dimension(fromAccountField.getPreferredSize().width, 40)); // Fixed height
        fromAccountField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(180, 180, 180), 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        inputContainerPanel.add(fromAccountField, gbc);

        // Reset weights for the next row to prevent unintended expansion
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.EAST; // Reset anchor for next label

        // --- To Account Field ---
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel toAccountLabel = new JLabel("To Account Number:");
        toAccountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        toAccountLabel.setForeground(new Color(80, 80, 80));
        inputContainerPanel.add(toAccountLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JTextField toAccountField = new JTextField(15);
        toAccountField.setFont(new Font("Consolas", Font.PLAIN, 12));
        toAccountField.setForeground(Color.BLACK);
        toAccountField.setPreferredSize(new Dimension(toAccountField.getPreferredSize().width, 40));
        toAccountField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(180, 180, 180), 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        inputContainerPanel.add(toAccountField, gbc);

        // Reset weights for the next row
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.EAST;

        // --- Amount Field ---
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel amountLabel = new JLabel("Amount to Transfer:");
        amountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        amountLabel.setForeground(new Color(80, 80, 80));
        inputContainerPanel.add(amountLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JTextField amountField = new JTextField(15);
        amountField.setFont(new Font("Consolas", Font.PLAIN, 12));
        amountField.setForeground(Color.BLACK);
        amountField.setPreferredSize(new Dimension(amountField.getPreferredSize().width, 40));
        amountField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(180, 180, 180), 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        inputContainerPanel.add(amountField, gbc);

        // Reset weights for the button row
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;

        // --- Transfer Button ---
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2; // Span across both columns
        gbc.anchor = GridBagConstraints.CENTER; // Center the button horizontally
        gbc.insets = new Insets(30, 10, 10, 10); // More top padding for the button
        JButton transferButton = createStyledButton("Transfer Funds", new Color(230, 126, 34)); // Themed button
        inputContainerPanel.add(transferButton, gbc);

        // --- Back Button ---
        JButton backButton = createStyledButton("Back to ATM Menu", new Color(96, 125, 139));
        backButton.addActionListener(e -> parentATMPanel.showATMMainMenu());

        JPanel bottomButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 25)); // More vertical space below
        bottomButtonPanel.setBackground(getBackground());
        bottomButtonPanel.add(backButton);
        add(bottomButtonPanel, BorderLayout.SOUTH);

        // --- Action Listener for Transfer Button ---
        transferButton.addActionListener(e -> {
            String fromAccount = fromAccountField.getText().trim();
            String toAccount = toAccountField.getText().trim();
            String amountText = amountField.getText().trim();

            // Basic validation
            if (fromAccount.isEmpty() || toAccount.isEmpty() || amountText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double amount = Double.parseDouble(amountText);
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(this, "Please enter a positive amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // In a real application, you would integrate with your backend here:
                // Example: parentATMPanel.getATMController().processFundTransfer(fromAccount, toAccount, amount);
                JOptionPane.showMessageDialog(this,
                        String.format("Transferring $%,.2f from account %s to account %s.%nTransaction is being processed.", amount, fromAccount, toAccount),
                        "Fund Transfer Successful", JOptionPane.INFORMATION_MESSAGE);
                
                // Clear fields after successful transfer
                fromAccountField.setText("");
                toAccountField.setText("");
                amountField.setText("");

                // Optionally, navigate back or update balance display
                // parentATMPanel.showATMMainMenu();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount. Please enter numbers only (e.g., 100.00).", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /**
     * Helper method to create consistently styled buttons with enhanced hover effects.
     */
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(220, 55)); // Slightly larger button
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