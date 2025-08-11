package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CloseAccountPanel extends JPanel {

    private BankPanel parentBankPanel; // Reference to the BankPanel
    private JTextField accountNumberField;
    private JPasswordField pinField;
    private JButton closeAccountButton;
    private JLabel messageLabel; // To display feedback messages

    public CloseAccountPanel(BankPanel parentBankPanel) {
        this.parentBankPanel = parentBankPanel;
        System.out.println("CloseAccountPanel: Constructor initiated."); // Debug print

        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50)); // Padding

        // --- Title ---
        JLabel titleLabel = new JLabel("Close Account", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titleLabel.setForeground(new Color(52, 73, 94));
        add(titleLabel, BorderLayout.NORTH);

        // --- Message Label (moved for better visibility) ---
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        messageLabel.setPreferredSize(new Dimension(getWidth(), 30)); // Give it some height
        messageLabel.setOpaque(true); // Make it opaque so background color is visible
        messageLabel.setBackground(new Color(245, 245, 245)); // Match panel background initially
        // messageLabel added to centerContainer later

        // --- Input Panel ---
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(getBackground());
        inputPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                "Account Details",
                0,
                0,
                new Font("Segoe UI", Font.BOLD, 16),
                new Color(52, 73, 94)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; // Allow components to expand horizontally

        // Account Number
        JLabel accNumLabel = new JLabel("Account Number:");
        accNumLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        gbc.gridx = 0; gbc.gridy = 0; inputPanel.add(accNumLabel, gbc);

        // --- MODIFICATION HERE: Reduced column count for JTextField ---
        accountNumberField = new JTextField(15); // Reduced from 20 to 15 (suggested columns)
        accountNumberField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        gbc.gridx = 1; gbc.gridy = 0; inputPanel.add(accountNumberField, gbc);

        // PIN
        JLabel pinLabel = new JLabel("PIN:");
        pinLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        gbc.gridx = 0; gbc.gridy = 1; inputPanel.add(pinLabel, gbc);

        // --- MODIFICATION HERE: Reduced column count for JPasswordField ---
        pinField = new JPasswordField(15); // Reduced from 20 to 15 (suggested columns)
        pinField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        gbc.gridx = 1; gbc.gridy = 1; inputPanel.add(pinField, gbc);

        // Nested panel to arrange the message label and input panel
        JPanel centerContainer = new JPanel(new BorderLayout());
        centerContainer.setBackground(getBackground());
        centerContainer.add(messageLabel, BorderLayout.NORTH); // Message label on top
        centerContainer.add(inputPanel, BorderLayout.CENTER); // Input panel below

        add(centerContainer, BorderLayout.CENTER); // Add the container to the main panel's center

        // --- Controls Panel ---
        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        controlsPanel.setBackground(getBackground());

        closeAccountButton = new JButton("Close Account");
        styleButton(closeAccountButton, new Color(231, 76, 60)); // Red for close
        closeAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("CloseAccountPanel: 'Close Account' button clicked. Calling attemptCloseAccount()."); // Debug
                attemptCloseAccount();
            }
        });
        controlsPanel.add(closeAccountButton);

        JButton backButton = new JButton("Back to Bank Menu");
        styleButton(backButton, new Color(149, 165, 166)); // Gray for back
        backButton.addActionListener(e -> {
            System.out.println("CloseAccountPanel: 'Back' button clicked. Returning to BankMainMenu."); // Debug
            parentBankPanel.showBankMainMenu();
            clearFields(); // Clear fields when going back
            showMessage("", getBackground()); // Clear message
        });
        controlsPanel.add(backButton);

        add(controlsPanel, BorderLayout.SOUTH);
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
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
    }

    private void attemptCloseAccount() {
        System.out.println("CloseAccountPanel: Entering attemptCloseAccount() method."); // Debug
        String accountNumber = accountNumberField.getText().trim();
        String pin = new String(pinField.getPassword()).trim();

        System.out.println("CloseAccountPanel: Entered Acc: '" + accountNumber + "', PIN: '" + pin + "'"); // Debug

        if (accountNumber.isEmpty() || pin.isEmpty()) {
            showMessage("Please enter both account number and PIN.", Color.RED);
            System.out.println("CloseAccountPanel: Validation failed: Empty fields."); // Debug
            return;
        }

        // --- Placeholder for actual account validation and balance check ---
        // In a real application, you would interact with your backend/model here
        // For demonstration, let's simulate the logic with specific accounts:

        // Simulate Account 1: Valid, Zero Balance (allows closure)
        if (accountNumber.equals("123456789") && pin.equals("1234")) {
            System.out.println("CloseAccountPanel: Matched test account 123456789."); // Debug
            double accountBalance = getAccountBalance(accountNumber);
            if (accountBalance > 0) {
                showMessage("Account balance must be zero to close the account.", Color.ORANGE);
                System.out.println("CloseAccountPanel: Balance not zero: " + accountBalance); // Debug
            } else {
                // Proceed with account closure logic (Simulated)
                showMessage("Account " + accountNumber + " successfully closed.", Color.BLUE);
                System.out.println("CloseAccountPanel: Account " + accountNumber + " closed successfully."); // Debug
                clearFields(); // Clear fields after successful operation
            }
            return; // Exit after processing this account
        }

        // Simulate Account 2: Valid, Non-Zero Balance (prevents closure)
        if (accountNumber.equals("987654321") && pin.equals("4321")) {
            System.out.println("CloseAccountPanel: Matched test account 987654321."); // Debug
            double accountBalance = getAccountBalance(accountNumber); // This will return 500.0 for this account
            if (accountBalance > 0) {
                showMessage("Account balance must be zero to close the account.", Color.ORANGE);
                System.out.println("CloseAccountPanel: Balance not zero: " + accountBalance); // Debug
            } else {
                // This block should ideally not be reached for this simulated account
                showMessage("Account " + accountNumber + " successfully closed.", Color.BLUE);
                System.out.println("CloseAccountPanel: (ERROR) Account 987654321 should not be closable."); // Debug
                clearFields();
            }
            return; // Exit after processing this account
        }

        // If neither simulated account matched
        showMessage("Invalid account number or PIN.", Color.RED);
        System.out.println("CloseAccountPanel: Validation failed: Invalid credentials."); // Debug
    }

    // This method would typically interact with your Model/Controller to get actual balance
    private double getAccountBalance(String accountNumber) {
        System.out.println("CloseAccountPanel: Simulating getAccountBalance for: " + accountNumber); // Debug
        if (accountNumber.equals("123456789")) {
            return 0.0; // Simulate zero balance for successful closure
        } else if (accountNumber.equals("987654321")) {
            return 500.0; // Simulate non-zero balance
        }
        return -1.0; // Indicate account not found or other error (should be caught by initial validation)
    }

    private void showMessage(String message, Color color) {
        messageLabel.setText(message);
        messageLabel.setForeground(color);
        // If you're observing flickering or non-appearance, try revalidate/repaint here:
        messageLabel.revalidate();
        messageLabel.repaint();
    }

    private void clearFields() {
        accountNumberField.setText("");
        pinField.setText("");
    }
}