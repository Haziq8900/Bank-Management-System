package View;

import javax.swing.*;
import java.awt.*;

public class ATMMainMenuPanel extends JPanel {

    private ATMPanel parentATMPanel; // Reference to the parent ATMPanel

    public ATMMainMenuPanel(ATMPanel parentATMPanel) {
        this.parentATMPanel = parentATMPanel;
        setLayout(new GridBagLayout());
        setBackground(new Color(240, 248, 255)); // Consistent background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        // --- Create Buttons for ATM Services ---
        JButton cashWithdrawalButton = parentATMPanel.createATMMenuButton("Cash Withdrawal", new Color(46, 204, 113));
        JButton balanceInquiryButton = parentATMPanel.createATMMenuButton("Balance Inquiry", new Color(52, 152, 219));
        JButton changePINButton = parentATMPanel.createATMMenuButton("Change PIN", new Color(220, 120, 20));
        JButton miniStatementButton = parentATMPanel.createATMMenuButton("Mini Statement", new Color(127, 140, 141));

        // --- Add Action Listeners to Buttons ---
        cashWithdrawalButton.addActionListener(e -> parentATMPanel.showATMSubPanel("CashWithdrawal"));
        balanceInquiryButton.addActionListener(e -> parentATMPanel.showATMSubPanel("BalanceInquiry"));
        changePINButton.addActionListener(e -> {
            parentATMPanel.showATMSubPanel("ChangePIN");
            parentATMPanel.setMainFrameHeaderVisibility(false);
        });
        
        // --- UPDATED ACTION LISTENER FOR MINI STATEMENT ---
        miniStatementButton.addActionListener(e -> parentATMPanel.showATMSubPanel("MiniStatement"));

        // --- Add Buttons to Panel ---
        gbc.gridx = 0; gbc.gridy = 0; add(cashWithdrawalButton, gbc);
        gbc.gridx = 1; gbc.gridy = 0; add(balanceInquiryButton, gbc);
        gbc.gridx = 0; gbc.gridy = 1; add(changePINButton, gbc);
        gbc.gridx = 1; gbc.gridy = 1; add(miniStatementButton, gbc);
    }
}