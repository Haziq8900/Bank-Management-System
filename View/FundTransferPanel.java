package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FundTransferPanel extends JPanel {
    // Changed: private ATMPanel parentATMPanel;
    private BankPanel parentBankPanel; // Now takes BankPanel as parent

    // Changed constructor: public FundTransferPanel(ATMPanel parentATMPanel)
    public FundTransferPanel(BankPanel parentBankPanel) {
        this.parentBankPanel = parentBankPanel; // Assign the new parent
        setLayout(new BorderLayout(0, 30));
        setBackground(new Color(230, 240, 250));

        JLabel titleLabel = new JLabel("Fund Transfer", SwingConstants.CENTER);
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
        JLabel fromAccountLabel = new JLabel("From Account Number:");
        fromAccountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        fromAccountLabel.setForeground(new Color(80, 80, 80));
        inputContainerPanel.add(fromAccountLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JTextField fromAccountField = new JTextField(15);
        fromAccountField.setFont(new Font("Consolas", Font.PLAIN, 20));
        fromAccountField.setForeground(Color.BLACK);
        fromAccountField.setPreferredSize(new Dimension(fromAccountField.getPreferredSize().width, 40));
        fromAccountField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(180, 180, 180), 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        inputContainerPanel.add(fromAccountField, gbc);

        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.EAST;

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
        toAccountField.setFont(new Font("Consolas", Font.PLAIN, 20));
        toAccountField.setForeground(Color.BLACK);
        toAccountField.setPreferredSize(new Dimension(toAccountField.getPreferredSize().width, 40));
        toAccountField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(180, 180, 180), 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        inputContainerPanel.add(toAccountField, gbc);

        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.EAST;

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

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 10, 10, 10);
        JButton transferButton = createStyledButton("Transfer Funds", new Color(230, 126, 34));
        inputContainerPanel.add(transferButton, gbc);

        // Changed: parentATMPanel.showATMMainMenu()
        JButton backButton = createStyledButton("Back to Bank Menu", new Color(96, 125, 139));
        backButton.addActionListener(e -> parentBankPanel.showBankMainMenu()); // Call BankPanel's method

        JPanel bottomButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 25));
        bottomButtonPanel.setBackground(getBackground());
        bottomButtonPanel.add(backButton);
        add(bottomButtonPanel, BorderLayout.SOUTH);

        transferButton.addActionListener(e -> {
            String fromAccount = fromAccountField.getText().trim();
            String toAccount = toAccountField.getText().trim();
            String amountText = amountField.getText().trim();

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

                JOptionPane.showMessageDialog(this,
                        String.format("Transferring $%,.2f from account %s to account %s.%nTransaction is being processed.", amount, fromAccount, toAccount),
                        "Fund Transfer Successful", JOptionPane.INFORMATION_MESSAGE);
                
                fromAccountField.setText("");
                toAccountField.setText("");
                amountField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount. Please enter numbers only (e.g., 100.00).", "Input Error", JOptionPane.ERROR_MESSAGE);
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