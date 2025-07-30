package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChangePINPanel extends JPanel {
    private ATMPanel parentATMPanel;

    public ChangePINPanel(ATMPanel parentATMPanel) {
        this.parentATMPanel = parentATMPanel;
        setLayout(new BorderLayout());
        setBackground(new Color(230, 240, 250));

        JLabel titleLabel = new JLabel("Change PIN", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titleLabel.setForeground(new Color(220, 120, 20));
        add(titleLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(getBackground());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Current PIN:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        JPasswordField currentPinField = new JPasswordField(15);
        inputPanel.add(currentPinField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("New PIN:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        JPasswordField newPinField = new JPasswordField(15);
        inputPanel.add(newPinField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(new JLabel("Confirm New PIN:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        JPasswordField confirmPinField = new JPasswordField(15);
        inputPanel.add(confirmPinField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        JButton changePinButton = createStyledButton("Change PIN", new Color(220, 120, 20));
        inputPanel.add(changePinButton, gbc);

        add(inputPanel, BorderLayout.CENTER);

        JButton backButton = createStyledButton("Back to ATM Menu", new Color(96, 125, 139));
        backButton.addActionListener(e -> parentATMPanel.showATMMainMenu());

        JPanel bottomButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        bottomButtonPanel.setBackground(getBackground());
        bottomButtonPanel.add(backButton);
        add(bottomButtonPanel, BorderLayout.SOUTH);

        changePinButton.addActionListener(e -> {
            String currentPin = new String(currentPinField.getPassword());
            String newPin = new String(newPinField.getPassword());
            String confirmPin = new String(confirmPinField.getPassword());

            if (currentPin.isEmpty() || newPin.isEmpty() || confirmPin.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!newPin.equals(confirmPin)) {
                JOptionPane.showMessageDialog(this, "New PIN and Confirm PIN do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Add actual PIN change logic here
            JOptionPane.showMessageDialog(this, "PIN changed successfully (simulated)!", "PIN Change", JOptionPane.INFORMATION_MESSAGE);
            parentATMPanel.showATMMainMenu(); // Go back to ATM menu after action
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