package View;

import Backend.Account;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DepositPanel extends JPanel {
    // Changed: private ATMPanel parentATMPanel;
    private BankPanel parentBankPanel; // Now takes BankPanel as parent

    // Changed constructor: public DepositPanel(ATMPanel parentATMPanel)
    public DepositPanel(BankPanel parentBankPanel) {
        this.parentBankPanel = parentBankPanel; // Assign the new parent
        setLayout(new BorderLayout(0, 30));
        setBackground(new Color(230, 240, 250));

        JLabel titleLabel = new JLabel("Deposit Funds", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 38));
        titleLabel.setForeground(new Color(155, 89, 182)); // Purple color for Deposit theme
        titleLabel.setBorder(new EmptyBorder(25, 0, 15, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel inputContainerPanel = new JPanel(new GridBagLayout());
        inputContainerPanel.setBackground(new Color(255, 255, 255));
        inputContainerPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(221, 160, 221), 3, true), // Softer purple border, rounded
            new EmptyBorder(40, 60, 40, 60)
        ));
        inputContainerPanel.setPreferredSize(new Dimension(650, 300));
        inputContainerPanel.setMaximumSize(new Dimension(650, 300));
        inputContainerPanel.setMinimumSize(new Dimension(550, 250));

        JPanel centerWrapperPanel = new JPanel(new GridBagLayout());
        centerWrapperPanel.setBackground(getBackground());
        centerWrapperPanel.add(inputContainerPanel);
        add(centerWrapperPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.anchor = GridBagConstraints.EAST;

        gbc.gridx = 0; gbc.gridy = 0;
        JLabel accountLabel = new JLabel("Account Number:");
        accountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        accountLabel.setForeground(new Color(80, 80, 80));
        inputContainerPanel.add(accountLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JTextField accountField = new JTextField(15);
        accountField.setFont(new Font("Consolas", Font.PLAIN, 20));
        accountField.setForeground(Color.BLACK);
        accountField.setPreferredSize(new Dimension(accountField.getPreferredSize().width, 40));
        accountField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(180, 180, 180), 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        inputContainerPanel.add(accountField, gbc);

        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.EAST;

        gbc.gridx = 0; gbc.gridy = 1;
        JLabel amountLabel = new JLabel("Amount to Deposit:");
        amountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        amountLabel.setForeground(new Color(80, 80, 80));
        inputContainerPanel.add(amountLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JTextField amountField = new JTextField(15);
        amountField.setFont(new Font("Consolas", Font.PLAIN, 20));
        amountField.setForeground(Color.BLACK);
        amountField.setPreferredSize(new Dimension(amountField.getPreferredSize().width, 40));
        amountField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(180, 180, 180), 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        inputContainerPanel.add(amountField, gbc);

        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 10, 10, 10);
        JButton depositButton = createStyledButton("Deposit", new Color(155, 89, 182));
        inputContainerPanel.add(depositButton, gbc);

        depositButton.addActionListener(e -> {
            String accountNumber = accountField.getText().trim();
            String amountText = amountField.getText().trim();

            if (accountNumber.isEmpty() || amountText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Both account number and amount are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double amount = Double.parseDouble(amountText);
                if (amount < 1) {
                    JOptionPane.showMessageDialog(this, "Please enter a positive amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Account account = new Account(accountNumber);
                account.deposit(amount);
                JOptionPane.showMessageDialog(this,
                        String.format("Depositing %,.2f pkr into account %s.%nTransaction is being processed.", amount, accountNumber),
                        "Deposit Successful", JOptionPane.INFORMATION_MESSAGE);
                
                accountField.setText("");
                amountField.setText("");
                // parentBankPanel.showBankMainMenu(); // Optionally go back
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount. Please enter numbers only (e.g., 100.00).", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
            catch (Exception ex){
                JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);

            }
        });
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(220, 55));
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