package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MiniStatementPanel extends JPanel {

    private ATMPanel parentATMPanel;

    public MiniStatementPanel(ATMPanel parentATMPanel) {
        this.parentATMPanel = parentATMPanel;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 248, 255));
        setBorder(new EmptyBorder(50, 50, 50, 50));

        JLabel titleLabel = new JLabel("Mini Statement", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titleLabel.setForeground(new Color(25, 118, 210));
        add(titleLabel, BorderLayout.NORTH);

        // This JTextArea will display the mini-statement
        JTextArea statementArea = new JTextArea();
        statementArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        statementArea.setEditable(false);
        statementArea.setBackground(Color.WHITE);
        statementArea.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        // --- Dummy Data for the Mini-Statement ---
        // You will replace this with real data from your backend
        statementArea.setText(
                "--------------------------------------\n" +
                "Date        | Description     | Amount\n" +
                "--------------------------------------\n" +
                "2025-08-10  | Deposit         | +$500.00\n" +
                "2025-08-11  | Withdrawal      | -$50.00\n" +
                "2025-08-12  | POS Transaction | -$25.50\n" +
                "2025-08-13  | Withdrawal      | -$200.00\n" +
                "--------------------------------------\n"
        );

        JScrollPane scrollPane = new JScrollPane(statementArea);
        add(scrollPane, BorderLayout.CENTER);

        // Back button to return to the ATM Main Menu
        // This is the button you wanted to create
        JButton backButton = parentATMPanel.createATMMenuButton("Back", new Color(96, 125, 139));
        backButton.setPreferredSize(new Dimension(250, 60));
        
        // --- UPDATED ACTION LISTENER ---
        // The key "ATMMainMenu" is used to show the panel with the four services
        backButton.addActionListener(e -> parentATMPanel.showATMSubPanel("ATMMainMenu"));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 248, 255));
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}