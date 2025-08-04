package View;

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

    public ATMPanel(MainFrame parentMainFrame) {
        this.parentMainFrame = parentMainFrame;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245)); // Consistent light background

        // Initialize CardLayout and its container
        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);
        mainContentPanel.setBackground(getBackground()); // Match ATMPanel background
        add(mainContentPanel, BorderLayout.CENTER);

        // --- Add ATM Main Menu Panel ---
        ATMMainMenuPanel atmMainMenuPanel = new ATMMainMenuPanel(this);
        mainContentPanel.add(atmMainMenuPanel, "ATMMainMenu");

        // --- Add ATM Service Sub-Panels (ONLY ATM specific ones) ---
        // These are the panels that remain part of ATM services
        CashWithdrawalPanel cashWithdrawalPanel = new CashWithdrawalPanel(this);
        mainContentPanel.add(cashWithdrawalPanel, "CashWithdrawal");

        BalanceInquiryPanel balanceInquiryPanel = new BalanceInquiryPanel(this);
        mainContentPanel.add(balanceInquiryPanel, "BalanceInquiry");

        ChangePINPanel changePINPanel = new ChangePINPanel(this);
        mainContentPanel.add(changePINPanel, "ChangePIN");

        // REMOVED:
        // DepositPanel depositPanel = new DepositPanel(this); // This line and its addition are removed
        // FundTransferPanel fundTransferPanel = new FundTransferPanel(this); // This line and its addition are removed
        // BillPaymentPanel billPaymentPanel = new BillPaymentPanel(this); // This line and its addition are removed


        // Add a back button for the whole ATM section
        JButton backButton = createATMMenuButton("Back to Main Menu", new Color(96, 125, 139)); // Gray color
        backButton.addActionListener(e -> parentMainFrame.showServiceSelectionPanel());

        JPanel bottomButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        bottomButtonPanel.setBackground(getBackground());
        bottomButtonPanel.add(backButton);
        add(bottomButtonPanel, BorderLayout.SOUTH);

        // Show the initial ATM main menu when this panel is displayed
        showATMMainMenu();
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
     * Navigates back to the main ATM menu.
     */
    public void showATMMainMenu() {
        cardLayout.show(mainContentPanel, "ATMMainMenu");
    }
    /**
     * Method to control the visibility of the MainFrame's header panel.
     * This acts as a bridge from ATMMainMenuPanel to MainFrame.
     * @param visible true to show, false to hide.
     */
    public void setMainFrameHeaderVisibility(boolean visible) {
        parentMainFrame.setHeaderPanelVisibility(visible);
    }
}