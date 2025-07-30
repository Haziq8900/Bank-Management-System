package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ATMPanel extends JPanel {

    private MainFrame parentFrame; // Reference to the parent MainFrame
    private JPanel atmContentPanel; // Panel to hold ATM-specific views using CardLayout
    private CardLayout atmCardLayout; // CardLayout for ATM views

    public ATMPanel(MainFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());
        setBackground(new Color(220, 230, 240));

        // --- ATM Header Label (can be moved or removed if MainFrame header is sufficient) ---
        JLabel atmWelcomeLabel = new JLabel("Welcome to ATM Services!", SwingConstants.CENTER);
        atmWelcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        atmWelcomeLabel.setForeground(new Color(30, 90, 150));
        add(atmWelcomeLabel, BorderLayout.NORTH); // Placed at NORTH for better layout

        // --- ATM Content Panel with CardLayout ---
        atmCardLayout = new CardLayout();
        atmContentPanel = new JPanel(atmCardLayout);
        atmContentPanel.setBackground(new Color(240, 245, 249)); // Light background

        // 1. ATM Main Menu Panel
        ATMMainMenuPanel atmMainMenuPanel = new ATMMainMenuPanel(this); // Pass ATMPanel reference
        atmContentPanel.add(atmMainMenuPanel, "ATMMainMenu");

        // 2. Cash Withdrawal Panel (Placeholder)
        CashWithdrawalPanel cashWithdrawalPanel = new CashWithdrawalPanel(this);
        atmContentPanel.add(cashWithdrawalPanel, "CashWithdrawal");

        // 3. Balance Inquiry Panel (Placeholder)
        BalanceInquiryPanel balanceInquiryPanel = new BalanceInquiryPanel(this);
        atmContentPanel.add(balanceInquiryPanel, "BalanceInquiry");

        // 4. Deposit Panel (Placeholder)
        DepositPanel depositPanel = new DepositPanel(this);
        atmContentPanel.add(depositPanel, "Deposit");

        // 5. Fund Transfer Panel (Placeholder)
        FundTransferPanel fundTransferPanel = new FundTransferPanel(this);
        atmContentPanel.add(fundTransferPanel, "FundTransfer");

        // 6. Change PIN Panel (Placeholder)
        ChangePINPanel changePINPanel = new ChangePINPanel(this);
        atmContentPanel.add(changePINPanel, "ChangePIN");

        // Add atmContentPanel to the center of ATMPanel
        add(atmContentPanel, BorderLayout.CENTER);

        // --- Back to Main Menu Button (at the bottom of ATMPanel) ---
        JButton backToMainFrameButton = new JButton("Exit ATM & Back to Main Menu");
        backToMainFrameButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        backToMainFrameButton.setBackground(new Color(244, 67, 54));
        backToMainFrameButton.setForeground(Color.WHITE);
        backToMainFrameButton.setPreferredSize(new Dimension(280, 50));
        backToMainFrameButton.setFocusPainted(false);

        backToMainFrameButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                backToMainFrameButton.setBackground(new Color(255, 87, 34));
            }
            public void mouseExited(MouseEvent evt) {
                backToMainFrameButton.setBackground(new Color(244, 67, 54));
            }
        });

        backToMainFrameButton.addActionListener(e -> {
            parentFrame.showServiceSelectionPanel(); // Go back to MainFrame's initial view
            showATMMainMenu(); // Reset ATM to its main menu when exiting to MainFrame
        });

        JPanel bottomButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        bottomButtonPanel.setBackground(new Color(220, 230, 240));
        bottomButtonPanel.add(backToMainFrameButton);
        add(bottomButtonPanel, BorderLayout.SOUTH);

        // Initially show the ATM main menu
        showATMMainMenu();
    }

    /**
     * Helper method to show a specific ATM sub-panel.
     * @param panelName The name of the panel to show (e.g., "CashWithdrawal", "BalanceInquiry").
     */
    public void showATMSubPanel(String panelName) {
        atmCardLayout.show(atmContentPanel, panelName);
    }

    /**
     * Method to navigate back to the ATM main menu.
     */
    public void showATMMainMenu() {
        atmCardLayout.show(atmContentPanel, "ATMMainMenu");
    }

    /**
     * Helper method to create consistently styled buttons for ATM menu.
     */
    public JButton createATMMenuButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(250, 60)); // Standard size for ATM options
        button.setFont(new Font("Segoe UI", Font.BOLD, 20));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker(), 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
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