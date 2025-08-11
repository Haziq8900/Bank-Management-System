package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BankPanel extends JPanel {

    private MainFrame parentMainFrame; // Reference to the MainFrame
    private JPanel mainContentPanel; // Panel for CardLayout of bank services
    private CardLayout cardLayout; // CardLayout for bank service sub-panels

    public BankPanel(MainFrame parentMainFrame) {
        this.parentMainFrame = parentMainFrame;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245)); // Consistent light background

        // Initialize CardLayout and its container
        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);
        mainContentPanel.setBackground(getBackground()); // Match BankPanel background
        add(mainContentPanel, BorderLayout.CENTER);

        // --- Add Bank Main Menu Panel ---
        JPanel bankMainMenuPanel = createBankMainMenuPanel();
        mainContentPanel.add(bankMainMenuPanel, "BankMainMenu");

        // --- Add Bank Service Sub-Panels (e.g., Deposit, Fund Transfer, Open Account, Close Account) ---
        DepositPanel depositPanel = new DepositPanel(this); // Pass this BankPanel
        mainContentPanel.add(depositPanel, "Deposit");

        FundTransferPanel fundTransferPanel = new FundTransferPanel(this); // Pass this BankPanel
        mainContentPanel.add(fundTransferPanel, "FundTransfer");

        OpenAccountPanel openAccountPanel = new OpenAccountPanel(this); // New Open Account Panel
        mainContentPanel.add(openAccountPanel, "OpenAccount");

        // Instantiate and add the CloseAccountPanel
        CloseAccountPanel closeAccountPanel = new CloseAccountPanel(this); // New Close Account Panel
        mainContentPanel.add(closeAccountPanel, "CloseAccount");

        // Show the initial bank main menu
        showBankMainMenu();
    }

    /**
     * Creates the main menu panel for Bank Services.
     */
    private JPanel createBankMainMenuPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255)); // Similar to ATMMainMenuPanel background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        // --- Bank Service Buttons ---
        JButton depositButton = createBankMenuButton("Deposit Funds", new Color(155, 89, 182)); // Purple
        JButton fundTransferButton = createBankMenuButton("Fund Transfer", new Color(230, 126, 34)); // Orange
        JButton openAccountButton = createBankMenuButton("Open New Account", new Color(39, 174, 96)); // Light Green
        // Replaced Bill Payment with Close Account
        JButton closeAccountButton = createBankMenuButton("Close Account", new Color(231, 76, 60)); // Red for Close Account

        JButton backButton = createBankMenuButton("Back to Main Menu", new Color(96, 125, 139)); // Gray

        // --- Add Action Listeners ---
        depositButton.addActionListener(e -> showBankSubPanel("Deposit"));
        fundTransferButton.addActionListener(e -> showBankSubPanel("FundTransfer"));
<<<<<<< HEAD
        openAccountButton.addActionListener(e -> showBankSubPanel("OpenAccount"));
        // Action listener for the new Close Account button
        closeAccountButton.addActionListener(e -> showBankSubPanel("CloseAccount"));
=======
        billPaymentButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Bill Payment function coming soon!", "Bank Service", JOptionPane.INFORMATION_MESSAGE));
        openAccountButton.addActionListener(e -> {
            showBankSubPanel("OpenAccount");
            parentMainFrame.setHeaderPanelVisibility(false);
        });
>>>>>>> d7e395c21899dc798f7fb1078165368403704dd5
        backButton.addActionListener(e -> parentMainFrame.showServiceSelectionPanel()); // Go back to MainFrame's service selection

        // --- Add Buttons to Panel ---
        gbc.gridx = 0; gbc.gridy = 0; panel.add(depositButton, gbc);
        gbc.gridx = 1; gbc.gridy = 0; panel.add(fundTransferButton, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(openAccountButton, gbc);
        gbc.gridx = 1; gbc.gridy = 1; panel.add(closeAccountButton, gbc); // Add the Close Account button here

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2; // Span across two columns
        gbc.insets = new Insets(40, 20, 20, 20); // More top padding for back button
        panel.add(backButton, gbc);

        return panel;
    }

    /**
     * Helper method to create consistently styled buttons for Bank Menu.
     */
    public JButton createBankMenuButton(String text, Color bgColor) {
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
     * Shows a specific sub-panel within the Bank services.
     * @param panelName The name of the panel to show (e.g., "Deposit", "FundTransfer", "OpenAccount", "CloseAccount").
     */
    public void showBankSubPanel(String panelName) {
        cardLayout.show(mainContentPanel, panelName);
    }

    /**
     * Navigates back to the main Bank menu.
     */
    public void showBankMainMenu() {
        cardLayout.show(mainContentPanel, "BankMainMenu");
    }
}