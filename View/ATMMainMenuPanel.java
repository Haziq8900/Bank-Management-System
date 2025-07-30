package View;

import javax.swing.*;
import java.awt.*;
// REMOVED these imports:
// import javax.swing.border.EmptyBorder;
// import javax.swing.plaf.basic.BasicButtonUI;
// import java.awt.event.MouseAdapter;
// import java.awt.event.MouseEvent;

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
        // These calls should be correct IF ATMPanel has the createATMMenuButton method
        // which then calls MainFrame.createStyledButton.
        JButton cashWithdrawalButton = parentATMPanel.createATMMenuButton("Cash Withdrawal", new Color(46, 204, 113));
        JButton balanceInquiryButton = parentATMPanel.createATMMenuButton("Balance Inquiry", new Color(52, 152, 219));
        JButton depositButton = parentATMPanel.createATMMenuButton("Deposit Funds", new Color(155, 89, 182));
        JButton fundTransferButton = parentATMPanel.createATMMenuButton("Fund Transfer", new Color(230, 126, 34));
        JButton changePINButton = parentATMPanel.createATMMenuButton("Change PIN", new Color(220, 120, 20));
        JButton miniStatementButton = parentATMPanel.createATMMenuButton("Mini Statement", new Color(127, 140, 141));
        JButton billPaymentButton = parentATMPanel.createATMMenuButton("Bill Payment", new Color(41, 128, 185));

        // --- Add Action Listeners to Buttons (unchanged) ---
        cashWithdrawalButton.addActionListener(e -> parentATMPanel.showATMSubPanel("CashWithdrawal"));
        balanceInquiryButton.addActionListener(e -> parentATMPanel.showATMSubPanel("BalanceInquiry"));
        depositButton.addActionListener(e -> parentATMPanel.showATMSubPanel("Deposit"));
        fundTransferButton.addActionListener(e -> parentATMPanel.showATMSubPanel("FundTransfer"));
        changePINButton.addActionListener(e -> parentATMPanel.showATMSubPanel("ChangePIN"));
        miniStatementButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Mini Statement function coming soon!", "ATM Service", JOptionPane.INFORMATION_MESSAGE));
        billPaymentButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Bill Payment function coming soon!", "ATM Service", JOptionPane.INFORMATION_MESSAGE));

        // --- Add Buttons to Panel (unchanged layout) ---
        gbc.gridx = 0; gbc.gridy = 0; add(cashWithdrawalButton, gbc);
        gbc.gridx = 1; gbc.gridy = 0; add(balanceInquiryButton, gbc);
        gbc.gridx = 0; gbc.gridy = 1; add(depositButton, gbc);
        gbc.gridx = 1; gbc.gridy = 1; add(fundTransferButton, gbc);
        gbc.gridx = 0; gbc.gridy = 2; add(changePINButton, gbc);
        gbc.gridx = 1; gbc.gridy = 2; add(miniStatementButton, gbc);
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(billPaymentButton, gbc);
        gbc.gridwidth = 1;
    }

    // MAKE SURE there is NO createStyledButton or createATMMenuButton method here
}