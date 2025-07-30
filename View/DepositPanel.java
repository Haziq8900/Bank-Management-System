package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DepositPanel extends JPanel {
    private ATMPanel parentATMPanel;

    public DepositPanel(ATMPanel parentATMPanel) {
        this.parentATMPanel = parentATMPanel;
        setLayout(new BorderLayout(0, 30));
        setBackground(new Color(230, 240, 250));

        // --- Title Label ---
        JLabel titleLabel = new JLabel("Deposit Funds", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 38));
        titleLabel.setForeground(new Color(155, 89, 182));
        titleLabel.setBorder(new EmptyBorder(25, 0, 15, 0));
        add(titleLabel, BorderLayout.NORTH);

        // --- Input Area (Central Focus resembling a transaction screen) ---
        JPanel inputContainerPanel = new JPanel(new GridBagLayout());
        inputContainerPanel.setBackground(new Color(255, 255, 255));
        inputContainerPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(190, 150, 220), 3, true),
            new EmptyBorder(40, 60, 40, 60)
        ));
        inputContainerPanel.setPreferredSize(new Dimension(550, 250));
        inputContainerPanel.setMaximumSize(new Dimension(550, 250));
        inputContainerPanel.setMinimumSize(new Dimension(450, 200));

        JPanel centerWrapperPanel = new JPanel(new GridBagLayout());
        centerWrapperPanel.setBackground(getBackground());
        centerWrapperPanel.add(inputContainerPanel);
        add(centerWrapperPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.anchor = GridBagConstraints.EAST;

        // Label for amount input
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel amountLabel = new JLabel("Enter Amount to Deposit:");
        amountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        amountLabel.setForeground(new Color(80, 80, 80));
        inputContainerPanel.add(amountLabel, gbc);

        // Text field for amount input
        gbc.gridx = 1; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Make the component fill horizontally
        gbc.weightx = 1.0; // Give all extra horizontal space to this column

        JTextField amountField = new JTextField(12);
        amountField.setFont(new Font("Consolas", Font.PLAIN, 24));
        amountField.setForeground(Color.BLACK); // Ensure text color is black
        amountField.setPreferredSize(new Dimension(amountField.getPreferredSize().width, 40)); // Keep fixed height, let weightx manage width

        amountField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(180, 180, 180), 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        inputContainerPanel.add(amountField, gbc);

        // IMPORTANT: Reset fill and weightx for the next component if it should not expand
        // This is good practice to prevent unintended layout behavior for subsequent components.
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;

        // Deposit Button
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 2; // Span across two columns
        gbc.anchor = GridBagConstraints.CENTER; // Center the button horizontally
        gbc.insets = new Insets(25, 10, 10, 10); // More top padding for the button
        JButton depositButton = createStyledButton("Deposit", new Color(155, 89, 182));
        inputContainerPanel.add(depositButton, gbc);

        // --- Back Button ---
        JButton backButton = createStyledButton("Back to ATM Menu", new Color(96, 125, 139));
        backButton.addActionListener(e -> parentATMPanel.showATMMainMenu());

        JPanel bottomButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 25));
        bottomButtonPanel.setBackground(getBackground());
        bottomButtonPanel.add(backButton);
        add(bottomButtonPanel, BorderLayout.SOUTH);

        // --- Action Listener for Deposit Button ---
        depositButton.addActionListener(e -> {
            String amountText = amountField.getText().trim();
            if (amountText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter an amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                double amount = Double.parseDouble(amountText);
                if (amount > 0) {
                    JOptionPane.showMessageDialog(this,
                            String.format("Successfully deposited $%,.2f.%nYour transaction is being processed.", amount),
                            "Deposit Successful", JOptionPane.INFORMATION_MESSAGE);
                    amountField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Please enter a positive amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
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