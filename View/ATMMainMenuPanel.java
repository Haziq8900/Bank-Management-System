package View;

import javax.swing.*;
import java.awt.*;

public class ATMMainMenuPanel extends JPanel {

    private ATMPanel parentATMPanel; // Reference to the parent ATMPanel

    public ATMMainMenuPanel(ATMPanel parentATMPanel) {
        this.parentATMPanel = parentATMPanel;
        setLayout(new GridBagLayout()); // Using GridBagLayout for flexible arrangement
        setBackground(new Color(240, 245, 249));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15); // Padding between buttons
        gbc.fill = GridBagConstraints.BOTH; // Buttons fill their grid cells

        // --- Create Buttons for ATM Services ---
        JButton cashWithdrawalButton = parentATMPanel.createATMMenuButton("Cash Withdrawal", new Color(46, 204, 113)); // Green
        JButton balanceInquiryButton = parentATMPanel.createATMMenuButton("Balance Inquiry", new Color(52, 152, 219)); // Blue
        JButton depositButton = parentATMPanel.createATMMenuButton("Deposit Funds", new Color(155, 89, 182)); // Purple
        JButton fundTransferButton = parentATMPanel.createATMMenuButton("Fund Transfer", new Color(230, 126, 34)); // Orange
        JButton changePINButton = parentATMPanel.createATMMenuButton("Change PIN", new Color(220, 120, 20)); // Darker orange
        JButton miniStatementButton = parentATMPanel.createATMMenuButton("Mini Statement", new Color(127, 140, 141)); // Gray
        JButton billPaymentButton = parentATMPanel.createATMMenuButton("Bill Payment", new Color(41, 128, 185)); // Darker blue

        // --- Add Action Listeners to Buttons ---
        cashWithdrawalButton.addActionListener(e -> parentATMPanel.showATMSubPanel("CashWithdrawal"));
        balanceInquiryButton.addActionListener(e -> parentATMPanel.showATMSubPanel("BalanceInquiry"));
        depositButton.addActionListener(e -> parentATMPanel.showATMSubPanel("Deposit"));
        fundTransferButton.addActionListener(e -> parentATMPanel.showATMSubPanel("FundTransfer"));
        changePINButton.addActionListener(e -> parentATMPanel.showATMSubPanel("ChangePIN"));
        miniStatementButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Mini Statement function coming soon!", "ATM Service", JOptionPane.INFORMATION_MESSAGE));
        billPaymentButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Bill Payment function coming soon!", "ATM Service", JOptionPane.INFORMATION_MESSAGE));


        // --- Add Buttons to Panel using GridBagLayout ---
        gbc.gridx = 0; gbc.gridy = 0; add(cashWithdrawalButton, gbc);
        gbc.gridx = 1; gbc.gridy = 0; add(balanceInquiryButton, gbc);
        gbc.gridx = 0; gbc.gridy = 1; add(depositButton, gbc);
        gbc.gridx = 1; gbc.gridy = 1; add(fundTransferButton, gbc);
        gbc.gridx = 0; gbc.gridy = 2; add(changePINButton, gbc);
        gbc.gridx = 1; gbc.gridy = 2; add(miniStatementButton, gbc);
        gbc.gridx = 0; gbc.gridy = 3; add(billPaymentButton, gbc);
        // Add more buttons as needed, adjusting gridx and gridy
    }
}