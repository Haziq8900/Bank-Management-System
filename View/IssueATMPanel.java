package View;

import Backend.Account;
import Model.AccountDatabase;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class IssueATMPanel extends JPanel {
    // Changed: private ATMPanel parentATMPanel;
    private BankPanel parentBankPanel; // Now takes BankPanel as parent

    // Changed constructor: public FundTransferPanel(ATMPanel parentATMPanel)
    public IssueATMPanel(BankPanel parentBankPanel) {
        this.parentBankPanel = parentBankPanel; // Assign the new parent
        setLayout(new BorderLayout(0, 30));
        setBackground(new Color(230, 240, 250));

        JLabel titleLabel = new JLabel("Issue ATM Card", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 38));
        titleLabel.setForeground(new Color(230, 126, 34)); // Orange color for the Fund Transfer theme
        titleLabel.setBorder(new EmptyBorder(25, 0, 15, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel inputContainerPanel = new JPanel(new GridBagLayout());
        inputContainerPanel.setBackground(new Color(255, 255, 255));
        inputContainerPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(255, 179, 102), 3, true), // Softer orange border, rounded
                new EmptyBorder(40, 60, 40, 60)
        ));
        inputContainerPanel.setPreferredSize(new Dimension(650, 350));
        inputContainerPanel.setMaximumSize(new Dimension(650, 350));
        inputContainerPanel.setMinimumSize(new Dimension(550, 300));

        JPanel centerWrapperPanel = new JPanel(new GridBagLayout());
        centerWrapperPanel.setBackground(getBackground());
        centerWrapperPanel.add(inputContainerPanel);
        add(centerWrapperPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.anchor = GridBagConstraints.EAST;

        gbc.gridx = 0; gbc.gridy = 0;
        JLabel accountNumberLabel = new JLabel("Account Number:");
        accountNumberLabel.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        accountNumberLabel.setForeground(new Color(80, 80, 80));
        inputContainerPanel.add(accountNumberLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JTextField accountNumberField = new JTextField(15);
        accountNumberField.setFont(new Font("Consolas", Font.PLAIN, 20));
        accountNumberField.setForeground(Color.BLACK);
        accountNumberField.setPreferredSize(new Dimension(accountNumberField.getPreferredSize().width, 40));
        accountNumberField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(180, 180, 180), 1),
                new EmptyBorder(5, 10, 5, 10)
        ));
        inputContainerPanel.add(accountNumberField, gbc);

        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.EAST;

        gbc.gridx = 0; gbc.gridy = 1;
        JLabel pinLabel = new JLabel("PIN");
        pinLabel.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        pinLabel.setForeground(new Color(80, 80, 80));
        inputContainerPanel.add(pinLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JTextField pinField = new JTextField(15);
        pinField.setFont(new Font("Consolas", Font.PLAIN, 20));
        pinField.setForeground(Color.BLACK);
        pinField.setPreferredSize(new Dimension(pinField.getPreferredSize().width, 40));
        pinField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(180, 180, 180), 1),
                new EmptyBorder(5, 10, 5, 10)
        ));
        inputContainerPanel.add(pinField, gbc);

        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.EAST;

        gbc.gridx = 0; gbc.gridy = 2;
        JLabel pinConfirmationLabel = new JLabel("PIN Confirmation");
        pinConfirmationLabel.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        pinConfirmationLabel.setForeground(new Color(80, 80, 80));
        inputContainerPanel.add(pinConfirmationLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JTextField pinConfirmationField = new JTextField(15);
        pinConfirmationField.setFont(new Font("Consolas", Font.PLAIN, 20));
        pinConfirmationField.setForeground(Color.BLACK);
        pinConfirmationField.setPreferredSize(new Dimension(pinConfirmationField.getPreferredSize().width, 40));
        pinConfirmationField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(180, 180, 180), 1),
                new EmptyBorder(5, 10, 5, 10)
        ));
        inputContainerPanel.add(pinConfirmationField, gbc);

        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 10, 10, 10);
        JButton transferButton = createStyledButton("Transfer Funds", new Color(230, 126, 34));
        inputContainerPanel.add(transferButton, gbc);

        transferButton.addActionListener(e -> {
            String accountNumber = accountNumberField.getText().trim();
            String pin = pinField.getText().trim();
            String pinConfirmation = pinConfirmationField.getText().trim();

            if (accountNumber.isEmpty() || pin.isEmpty() || pinConfirmation.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (accountNumber.length() != 16) {
                JOptionPane.showMessageDialog(this, "Account number must be 16 digits.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(pin.length() != 4){
                JOptionPane.showMessageDialog(this, "PIN must be 4 digits.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(pinConfirmation.length() != 4){
                JOptionPane.showMessageDialog(this, "PIN confirmation must be 4 digits.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(!pinConfirmation.equals(pin)){
                JOptionPane.showMessageDialog(this, "PIN is not match", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Account account = new Account(accountNumber);
                account.setPin(pin);
                AccountDatabase accountDatabase = new AccountDatabase();
                accountDatabase.issueATM(account);

                JOptionPane.showMessageDialog(this,
                        "ATM issued successfully of " +  accountNumber,
                        "ATM Issue", JOptionPane.INFORMATION_MESSAGE);

                accountNumberField.setText("");
                pinField.setText("");
                pinConfirmationField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid. Please enter numbers only", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
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