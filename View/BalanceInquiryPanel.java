package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BalanceInquiryPanel extends JPanel {

    private ATMPanel parentATMPanel;
    private JLabel balanceDisplayLabel; // To display balance dynamically

    public BalanceInquiryPanel(ATMPanel parentATMPanel) {
        this.parentATMPanel = parentATMPanel;
        setLayout(new BorderLayout());
        setBackground(new Color(230, 240, 250));

        JLabel titleLabel = new JLabel("Balance Inquiry", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titleLabel.setForeground(new Color(52, 152, 219)); // Blue color
        add(titleLabel, BorderLayout.NORTH);

        balanceDisplayLabel = new JLabel("Your current balance: $XXXX.XX", SwingConstants.CENTER);
        balanceDisplayLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        balanceDisplayLabel.setForeground(new Color(30, 90, 150));
        add(balanceDisplayLabel, BorderLayout.CENTER);

        // --- Back Button ---
        JButton backButton = createStyledButton("Back to ATM Menu", new Color(96, 125, 139));
        backButton.addActionListener(e -> parentATMPanel.showATMMainMenu());

        JPanel bottomButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        bottomButtonPanel.setBackground(getBackground());
        bottomButtonPanel.add(backButton);
        add(bottomButtonPanel, BorderLayout.SOUTH);

        // You'd typically fetch the balance from your model/database here
        // For demonstration, let's update it when the panel is shown (in a real app, this might be triggered by a specific event)
        // updateBalanceDisplay(1234.56); // Example
    }

    public void updateBalanceDisplay(double balance) {
        balanceDisplayLabel.setText("Your current balance: $" + String.format("%.2f", balance));
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