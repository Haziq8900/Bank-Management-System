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

        // --- Create Buttons for ATM Services (Removed Deposit, Fund Transfer, Bill Payment) ---
        JButton cashWithdrawalButton = parentATMPanel.createATMMenuButton("Cash Withdrawal", new Color(46, 204, 113));
        JButton balanceInquiryButton = parentATMPanel.createATMMenuButton("Balance Inquiry", new Color(52, 152, 219));
        JButton changePINButton = parentATMPanel.createATMMenuButton("Change PIN", new Color(220, 120, 20));
        JButton miniStatementButton = parentATMPanel.createATMMenuButton("Mini Statement", new Color(127, 140, 141));
        // Removed: depositButton, fundTransferButton, billPaymentButton

        // --- Add Action Listeners to Buttons ---
        cashWithdrawalButton.addActionListener(e -> parentATMPanel.showATMSubPanel("CashWithdrawal"));
        balanceInquiryButton.addActionListener(e -> parentATMPanel.showATMSubPanel("BalanceInquiry"));
        changePINButton.addActionListener(e -> {
            parentATMPanel.showATMSubPanel("ChangePIN");
            parentATMPanel.setMainFrameHeaderVisibility(false);
        });
        miniStatementButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Mini Statement function coming soon!", "ATM Service", JOptionPane.INFORMATION_MESSAGE));
        // Removed action listeners for deposit, fund transfer, bill payment

        // --- Add Buttons to Panel (Adjusted layout for 4 buttons) ---
        gbc.gridx = 0; gbc.gridy = 0; add(cashWithdrawalButton, gbc);
        gbc.gridx = 1; gbc.gridy = 0; add(balanceInquiryButton, gbc);
        gbc.gridx = 0; gbc.gridy = 1; add(changePINButton, gbc);
        gbc.gridx = 1; gbc.gridy = 1; add(miniStatementButton, gbc);
        // Removed rows for removed buttons. The layout now fits 2x2.
    }
    // MAKE SURE there is NO createStyledButton or createATMMenuButton method here - these should be in ATMPanel
}  