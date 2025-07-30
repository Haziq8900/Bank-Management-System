package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FundTransferPanel extends JPanel {
    private ATMPanel parentATMPanel;

    public FundTransferPanel(ATMPanel parentATMPanel) {
        this.parentATMPanel = parentATMPanel;
        setLayout(new BorderLayout());
        setBackground(new Color(230, 240, 250));

        JLabel titleLabel = new JLabel("Fund Transfer", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titleLabel.setForeground(new Color(230, 126, 34));
        add(titleLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(getBackground());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("From Account:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        JTextField fromAccountField = new JTextField(15);
        inputPanel.add(fromAccountField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("To Account:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        JTextField toAccountField = new JTextField(15);
        inputPanel.add(toAccountField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(new JLabel("Amount:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        JTextField amountField = new JTextField(15);
        inputPanel.add(amountField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        JButton transferButton = createStyledButton("Transfer Funds", new Color(230, 126, 34));
        inputPanel.add(transferButton, gbc);

        add(inputPanel, BorderLayout.CENTER);

        JButton backButton = createStyledButton("Back to ATM Menu", new Color(96, 125, 139));
        backButton.addActionListener(e -> parentATMPanel.showATMMainMenu());

        JPanel bottomButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        bottomButtonPanel.setBackground(getBackground());
        bottomButtonPanel.add(backButton);
        add(bottomButtonPanel, BorderLayout.SOUTH);

        transferButton.addActionListener(e -> {
            // Basic validation
            if (fromAccountField.getText().isEmpty() || toAccountField.getText().isEmpty() || amountField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                double amount = Double.parseDouble(amountField.getText());
                if (amount > 0) {
                    JOptionPane.showMessageDialog(this, "Transferring $" + amount + " from " + fromAccountField.getText() + " to " + toAccountField.getText(), "Fund Transfer", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Please enter a valid amount.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount. Please enter numbers only.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(180, 50));
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker(), 2),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }
            public void mouseExited(MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        return button;
    }
}