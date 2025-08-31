package View;

import Backend.Account;
import Model.ATMDatabase;

import javax.swing.*;
import javax.swing.border.EmptyBorder; // Import for EmptyBorder
import javax.swing.border.LineBorder; // Import for LineBorder
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class ChangePINPanel extends JPanel {
    private ATMPanel parentATMPanel;

    public ChangePINPanel(ATMPanel parentATMPanel) {
        this.parentATMPanel = parentATMPanel;
        setLayout(new BorderLayout(0, 30)); // Add vertical gap for better spacing
        setBackground(new Color(230, 240, 250)); // Consistent light bluish background

        // --- Title Label ---
        JLabel titleLabel = new JLabel("Change PIN", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 38)); // Larger, bolder title for impact
        titleLabel.setForeground(new Color(220, 120, 20)); // Orange/brown color for the Change PIN theme
        titleLabel.setBorder(new EmptyBorder(25, 0, 15, 0)); // More padding around title
        add(titleLabel, BorderLayout.NORTH);

        // --- Input Area (Central Focus resembling a transaction screen) ---
        // This panel will contain all input fields and the change PIN button
        JPanel inputContainerPanel = new JPanel(new GridBagLayout());
        inputContainerPanel.setBackground(new Color(255, 255, 255)); // White background for the input area
        inputContainerPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(255, 179, 102), 3, true), // Softer orange border, rounded
            new EmptyBorder(10, 60, 10, 60) // Generous internal padding
        ));
        inputContainerPanel.setPreferredSize(new Dimension(600, 450)); // Increased height to accommodate all fields
        inputContainerPanel.setMaximumSize(new Dimension(600, 450));
        inputContainerPanel.setMinimumSize(new Dimension(500, 450));

        // Wrapper panel to visually center the inputContainerPanel within the BorderLayout.CENTER
        JPanel centerWrapperPanel = new JPanel(new GridBagLayout());
        centerWrapperPanel.setBackground(getBackground()); // Match main panel background
        centerWrapperPanel.add(inputContainerPanel);
        add(centerWrapperPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 10, 20, 10); // Further increased vertical insets for better spacing
        gbc.anchor = GridBagConstraints.EAST; // Labels align right by default

        // --- Account Number Field ---
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel accountNumberLabel = new JLabel("Account Number");
        accountNumberLabel.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        accountNumberLabel.setForeground(new Color(80, 80, 80));
        inputContainerPanel.add(accountNumberLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST; // Text field aligns left
        gbc.fill = GridBagConstraints.HORIZONTAL; // Make the component fill horizontally
        gbc.weightx = 1.0; // Give all extra horizontal space to this column
        JTextField accountNumberField = new JTextField(15);
        accountNumberField.setFont(new Font("Consolas", Font.PLAIN, 20)); // Consistent font size for input
        accountNumberField.setForeground(Color.BLACK); // Ensure text color is black
        accountNumberField.setPreferredSize(new Dimension(accountNumberField.getPreferredSize().width, 40)); // Fixed height
        accountNumberField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(180, 180, 180), 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        inputContainerPanel.add(accountNumberField, gbc);

        // Reset weights for the next row to prevent unintended expansion
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.EAST; // Reset anchor for next label

        // --- Current PIN Field ---
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel currentPinLabel = new JLabel("Current PIN");
        currentPinLabel.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        currentPinLabel.setForeground(new Color(80, 80, 80));
        inputContainerPanel.add(currentPinLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST; // Text field aligns left
        gbc.fill = GridBagConstraints.HORIZONTAL; // Make the component fill horizontally
        gbc.weightx = 1.0; // Give all extra horizontal space to this column
        JPasswordField currentPinField = new JPasswordField(15);
        currentPinField.setFont(new Font("Consolas", Font.PLAIN, 20)); // Consistent font size for input
        currentPinField.setForeground(Color.BLACK); // Ensure text color is black
        currentPinField.setPreferredSize(new Dimension(currentPinField.getPreferredSize().width, 40)); // Fixed height
        currentPinField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(180, 180, 180), 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        inputContainerPanel.add(currentPinField, gbc);

        // Reset weights for the next row to prevent unintended expansion
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.EAST; // Reset anchor for next label

        // --- New PIN Field ---
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel newPinLabel = new JLabel("New PIN");
        newPinLabel.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        newPinLabel.setForeground(new Color(80, 80, 80));
        inputContainerPanel.add(newPinLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JPasswordField newPinField = new JPasswordField(15);
        newPinField.setFont(new Font("Consolas", Font.PLAIN, 20));
        newPinField.setForeground(Color.BLACK);
        newPinField.setPreferredSize(new Dimension(newPinField.getPreferredSize().width, 40));
        newPinField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(180, 180, 180), 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        inputContainerPanel.add(newPinField, gbc);

        // Reset weights for the next row
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.EAST;

        // --- Confirm New PIN Field ---
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel confirmPinLabel = new JLabel("Confirm New PIN");
        confirmPinLabel.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        confirmPinLabel.setForeground(new Color(80, 80, 80));
        inputContainerPanel.add(confirmPinLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JPasswordField confirmPinField = new JPasswordField(15);
        confirmPinField.setFont(new Font("Consolas", Font.PLAIN, 20));
        confirmPinField.setForeground(Color.BLACK);
        confirmPinField.setPreferredSize(new Dimension(confirmPinField.getPreferredSize().width, 40));
        confirmPinField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(180, 180, 180), 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        inputContainerPanel.add(confirmPinField, gbc);

        // Reset weights for the button row
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;

        // --- Change PIN Button ---
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2; // Span across both columns
        gbc.anchor = GridBagConstraints.CENTER; // Center the button horizontally
        gbc.insets = new Insets(30, 10, 10, 10); // Increased top padding for better separation from fields
        JButton changePinButton = createStyledButton("Change PIN", new Color(220, 120, 20)); // Themed button
        inputContainerPanel.add(changePinButton, gbc);


        // --- Action Listener for Change PIN Button ---
        changePinButton.addActionListener(e -> {
            String accountNumber = accountNumberField.getText();
            String currentPin = new String(currentPinField.getPassword());
            String newPin = new String(newPinField.getPassword());
            String confirmPin = new String(confirmPinField.getPassword());


            try {
                if (accountNumber.isEmpty() || currentPin.isEmpty() || newPin.isEmpty() || confirmPin.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "All fields are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (newPin.length() != 4) { // Example PIN length validation
                    JOptionPane.showMessageDialog(this, "New PIN must be between 4 and 6 digits.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!newPin.equals(confirmPin)) {
                    JOptionPane.showMessageDialog(this, "New PIN and Confirm PIN do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (currentPin.equals(newPin)) {
                    JOptionPane.showMessageDialog(this, "New PIN cannot be the same as the current PIN.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ATMDatabase atmDatabase = new ATMDatabase();
                atmDatabase.changePin(accountNumber, currentPin, newPin);

                JOptionPane.showMessageDialog(this,
                        "Your PIN has been successfully changed.",
                        "PIN Change Successful", JOptionPane.INFORMATION_MESSAGE);

                // Clear fields after successful PIN change
                accountNumberField.setText("");
                currentPinField.setText("");
                newPinField.setText("");
                confirmPinField.setText("");

                parentATMPanel.showATMMainMenu(); // Go back to ATM menu after action
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /**
     * Helper method to create consistently styled buttons with enhanced hover effects.
     */
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(250, 45)); // Increased button size for better visibility
        button.setFont(new Font("Segoe UI", Font.BOLD, 20)); // Slightly larger font
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker(), 2),
                BorderFactory.createEmptyBorder(10, 25, 10, 25) // Increased horizontal padding
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
