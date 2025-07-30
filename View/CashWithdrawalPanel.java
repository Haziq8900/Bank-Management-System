package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CashWithdrawalPanel extends JPanel {

    private ATMPanel parentATMPanel;

    public CashWithdrawalPanel(ATMPanel parentATMPanel) {
        this.parentATMPanel = parentATMPanel;
        setLayout(new BorderLayout());
        setBackground(new Color(230, 240, 250)); // Slightly different background

        JLabel titleLabel = new JLabel("Cash Withdrawal", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titleLabel.setForeground(new Color(76, 175, 80)); // Green color
        add(titleLabel, BorderLayout.NORTH);

        // --- Placeholder for withdrawal specific inputs ---
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(getBackground());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Enter Amount:"), gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        JTextField amountField = new JTextField(15);
        inputPanel.add(amountField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 2;
        JButton withdrawButton = createStyledButton("Withdraw", new Color(46, 204, 113));
        inputPanel.add(withdrawButton, gbc);

        add(inputPanel, BorderLayout.CENTER);


        // --- Back Button ---
        JButton backButton = createStyledButton("Back to ATM Menu", new Color(96, 125, 139)); // Gray for internal back
        backButton.addActionListener(e -> parentATMPanel.showATMMainMenu());

        JPanel bottomButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        bottomButtonPanel.setBackground(getBackground());
        bottomButtonPanel.add(backButton);
        add(bottomButtonPanel, BorderLayout.SOUTH);

        // Example action for withdraw button (you'll replace with actual logic)
        withdrawButton.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                if (amount > 0) {
                    JOptionPane.showMessageDialog(this, "Withdrawing $" + amount + "...", "Withdrawal", JOptionPane.INFORMATION_MESSAGE);
                    // Add your actual withdrawal logic here
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