package View;

import Backend.Account;
import Model.AccountDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class CloseAccountPanel extends JPanel {

    private BankPanel parentBankPanel; // Reference to the BankPanel
    private JTextField accountNumberField;
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
                try {
                    attemptCloseAccount();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        controlsPanel.add(closeAccountButton);

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

    private void attemptCloseAccount() throws SQLException {
        try {
            System.out.println("CloseAccountPanel: Entering attemptCloseAccount() method."); // Debug
            String accountNumber = accountNumberField.getText().trim();

            System.out.println("CloseAccountPanel: Entered Acc: '" + accountNumber); // Debug

            if (accountNumber.isEmpty()) {
                showMessage("Please enter account number", Color.RED);
                System.out.println("CloseAccountPanel: Validation failed: Empty fields."); // Debug
                return;
            }

            Account account = new Account(accountNumber);
            AccountDatabase accountDatabase = new AccountDatabase();
            account.setBalance(accountDatabase.getBalance(account));


            if (account.getBalance() > 0) {
                showMessage("Account balance must be zero to close the account.", Color.ORANGE);
                System.out.println("CloseAccountPanel: Balance not zero: " + account.getBalance()); // Debug
            } else {
                accountDatabase.closeAccount(account);
                showMessage("Account " + accountNumber + " successfully closed.", Color.BLUE);
                System.out.println("CloseAccountPanel: Account " + accountNumber + " closed successfully."); // Debug
                clearFields(); // Clear fields after successful operation
            }
            return; // Exit after processing this account
        }catch (Exception ex){
            // If neither simulated account matched
            showMessage(ex.getMessage(), Color.RED);
            System.out.println("CloseAccountPanel: Validation failed: Invalid credentials."); // Debug
        }

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
    }
}