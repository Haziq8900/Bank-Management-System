package View;

import Backend.Account;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ATMPanel extends JPanel {

    private MainFrame parentMainFrame; // Reference to the MainFrame
    private JPanel mainContentPanel; // Panel for CardLayout of ATM services
    private CardLayout cardLayout; // CardLayout for ATM service sub-panels
    private Backend.Account account; // Account object for ATM operations

    public ATMPanel(MainFrame parentMainFrame) {
        this.parentMainFrame = parentMainFrame;
        this.account = null;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245)); // Consistent light background

        // Create sidebar panel for buttons
        JPanel sidebarPanel = createSidebarPanel();
        add(sidebarPanel, BorderLayout.WEST);

        // Initialize CardLayout and its container
        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);
        mainContentPanel.setBackground(getBackground()); // Match ATMPanel background
        add(mainContentPanel, BorderLayout.CENTER);

        // --- Add ATM Service Sub-Panels (ONLY ATM specific ones) ---
        // These are the panels that remain part of ATM services
        CashWithdrawalPanel cashWithdrawalPanel = new CashWithdrawalPanel(this);
        mainContentPanel.add(cashWithdrawalPanel, "CashWithdrawal");

        BalanceInquiryPanel balanceInquiryPanel = new BalanceInquiryPanel(this);
        mainContentPanel.add(balanceInquiryPanel, "BalanceInquiry");

        ChangePINPanel changePINPanel = new ChangePINPanel(this);
        mainContentPanel.add(changePINPanel, "ChangePIN");

        // --- NEW: Add the MiniStatementPanel to the CardLayout ---
        MiniStatementPanel miniStatementPanel = new MiniStatementPanel(this);
        mainContentPanel.add(miniStatementPanel, "MiniStatement");

        // REMOVED:
        // DepositPanel depositPanel = new DepositPanel(this); // This line and its addition are removed
        // FundTransferPanel fundTransferPanel = new FundTransferPanel(this); // This line and its addition are removed
        // BillPaymentPanel billPaymentPanel = new BillPaymentPanel(this); // This line and its addition are removed

        // Show the first service panel when this panel is displayed
        showATMSubPanel("CashWithdrawal");
    }

    /**
     * Helper method to create consistently styled buttons for ATM Menu.
     */
    public JButton createATMMenuButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(250, 80)); // Consistent size for menu buttons
        button.setFont(new Font("Segoe UI", Font.BOLD, 22)); // Font for menu buttons
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker(), 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(bgColor.brighter());
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(bgColor);
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        return button;
    }

    /**
     * Shows a specific sub-panel within the ATM services.
     * @param panelName The name of the panel to show (e.g., "CashWithdrawal", "BalanceInquiry", "ChangePIN").
     */
    public void showATMSubPanel(String panelName) {
        cardLayout.show(mainContentPanel, panelName);
    }

    /**
     * Navigates back to the service selection panel.
     * This is used by the back buttons in the ATM service panels.
     */
    public void showATMMainMenu() {
        parentMainFrame.showServiceSelectionPanel();
    }
    /**
     * Method to control the visibility of the MainFrame's header panel.
     * This is used by the Change PIN functionality to hide the header when needed.
     * @param visible true to show, false to hide.
     */
    public void setMainFrameHeaderVisibility(boolean visible) {
        parentMainFrame.setHeaderPanelVisibility(visible);
    }

    /**
     * Sets the account for this panel and its sub-panels.
     * @param account The account to use for ATM operations.
     */
    public void setAccount(Account account) {
        this.account = account;
        // Pass the account to the CashWithdrawalPanel
        CashWithdrawalPanel cashWithdrawalPanel = (CashWithdrawalPanel) mainContentPanel.getComponent(0);
        cashWithdrawalPanel.setAccount(account);
    }

    /**
     * Gets the account for this panel.
     * @return The account used for ATM operations.
     */
    public Backend.Account getAccount() {
        return account;
    }

    /**
     * Creates the sidebar panel with buttons for ATM services.
     */
    private JPanel createSidebarPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(52, 73, 94)); // Dark blue background
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setPreferredSize(new Dimension(280, 0)); // Fixed width for sidebar

        // --- ATM Service Buttons ---
        JButton cashWithdrawalButton = createSidebarButton("Cash Withdrawal", new Color(46, 204, 113)); // Green
        JButton balanceInquiryButton = createSidebarButton("Balance Inquiry", new Color(52, 152, 219)); // Blue
        JButton changePINButton = createSidebarButton("Change PIN", new Color(220, 120, 20)); // Orange
        JButton miniStatementButton = createSidebarButton("Mini Statement", new Color(127, 140, 141)); // Gray
        JButton backButton = createSidebarButton("Back to Main Menu", new Color(96, 125, 139)); // Gray

        // --- Add Action Listeners ---
        cashWithdrawalButton.addActionListener(e -> showATMSubPanel("CashWithdrawal"));
        balanceInquiryButton.addActionListener(e -> showATMSubPanel("BalanceInquiry"));
        changePINButton.addActionListener(e -> {
            showATMSubPanel("ChangePIN");
            setMainFrameHeaderVisibility(false);
        });
        miniStatementButton.addActionListener(e -> showATMSubPanel("MiniStatement"));
        backButton.addActionListener(e -> parentMainFrame.showServiceSelectionPanel()); // Go back to MainFrame's service selection

        // --- Add Buttons to Panel with spacing ---
        panel.add(cashWithdrawalButton);
        panel.add(Box.createRigidArea(new Dimension(0, 15))); // Add spacing
        panel.add(balanceInquiryButton);
        panel.add(Box.createRigidArea(new Dimension(0, 15))); // Add spacing
        panel.add(changePINButton);
        panel.add(Box.createRigidArea(new Dimension(0, 15))); // Add spacing
        panel.add(miniStatementButton);
        panel.add(Box.createVerticalGlue()); // Push remaining buttons to bottom
        panel.add(backButton);

        return panel;
    }

    /**
     * Helper method to create consistently styled buttons for Sidebar.
     */
    public JButton createSidebarButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60)); // Full width, fixed height
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Font for sidebar buttons
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker(), 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(bgColor.brighter());
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(bgColor);
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        return button;
    }
}
