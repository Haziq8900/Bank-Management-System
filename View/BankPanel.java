package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.NumberFormat;
import Model.AccountStatistics;

public class BankPanel extends JPanel {

    private MainFrame parentMainFrame; // Reference to the MainFrame
    private JPanel mainContentPanel; // Panel for CardLayout of bank services
    private CardLayout cardLayout; // CardLayout for bank service sub-panels
    private JPanel dashboardPanel; // Panel for dashboard with statistics
    private JLabel totalAccountsLabel;
    private JLabel savingsAccountsLabel;
    private JLabel currentAccountsLabel;
    private JLabel totalAmountLabel;


    public BankPanel(MainFrame parentMainFrame) {
        this.parentMainFrame = parentMainFrame;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245)); // Consistent light background

        // Create sidebar panel for buttons
        JPanel sidebarPanel = createSidebarPanel();
        add(sidebarPanel, BorderLayout.WEST);

        // Initialize CardLayout and its container
        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);
        mainContentPanel.setBackground(getBackground()); // Match BankPanel background
        add(mainContentPanel, BorderLayout.CENTER);

        // --- Create Dashboard Panel with Statistics ---
        dashboardPanel = createDashboardPanel();
        mainContentPanel.add(dashboardPanel, "Dashboard");

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

        IssueATMPanel issueATMPanel = new IssueATMPanel(this); // New Close Account Panel
        mainContentPanel.add(issueATMPanel, "IssueATM");
        // Show the initial dashboard
        showDashboard();
    }

    /**
     * Creates the sidebar panel with buttons for bank services.
     */
    private JPanel createSidebarPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(52, 73, 94)); // Dark blue background
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setPreferredSize(new Dimension(280, 0)); // Fixed width for sidebar

        // --- Bank Service Buttons ---
        JButton dashboardButton = createSidebarButton("Dashboard", new Color(41, 128, 185)); // Blue
        JButton depositButton = createSidebarButton("Deposit Funds", new Color(155, 89, 182)); // Purple
        JButton fundTransferButton = createSidebarButton("Fund Transfer", new Color(230, 126, 34)); // Orange
        JButton openAccountButton = createSidebarButton("Open New Account", new Color(39, 174, 96)); // Light Green
        JButton issueATMButton = createSidebarButton("Issue ATM", new Color(0, 150, 136)); // Teal for Issue ATM
        JButton closeAccountButton = createSidebarButton("Close Account", new Color(231, 76, 60)); // Red for Close Account
        JButton backButton = createSidebarButton("Back to Main Menu", new Color(96, 125, 139)); // Gray

        // --- Add Action Listeners ---
        dashboardButton.addActionListener(e -> showDashboard());
        depositButton.addActionListener(e -> showBankSubPanel("Deposit"));
        fundTransferButton.addActionListener(e -> showBankSubPanel("FundTransfer"));
        openAccountButton.addActionListener(e -> showBankSubPanel("OpenAccount"));
        closeAccountButton.addActionListener(e -> showBankSubPanel("CloseAccount"));
        issueATMButton.addActionListener(e -> showBankSubPanel("IssueATM"));
        backButton.addActionListener(e -> parentMainFrame.showServiceSelectionPanel()); // Go back to MainFrame's service selection

        // --- Add Buttons to Panel with spacing ---
        panel.add(dashboardButton);
        panel.add(Box.createRigidArea(new Dimension(0, 15))); // Add spacing
        panel.add(depositButton);
        panel.add(Box.createRigidArea(new Dimension(0, 15))); // Add spacing
        panel.add(fundTransferButton);
        panel.add(Box.createRigidArea(new Dimension(0, 15))); // Add spacing
        panel.add(openAccountButton);
        panel.add(Box.createRigidArea(new Dimension(0, 15))); // Add spacing
        panel.add(issueATMButton);
        panel.add(Box.createRigidArea(new Dimension(0, 15))); // Add spacing
        panel.add(closeAccountButton);
        panel.add(Box.createVerticalGlue()); // Push remaining buttons to bottom
        panel.add(backButton);

        return panel;
    }

    // Replace your createStatisticsCard(...) with this version:
    private JPanel createStatisticsCard(String title, String value, Color color, JLabel valueLabel) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(color);
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // The key part: assign the JLabel reference directly
        valueLabel.setText(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(titleLabel);
        card.add(Box.createRigidArea(new Dimension(0, 12)));
        card.add(valueLabel);

        return card;
    }

// In your createDashboardPanel, create *labels* ahead of time and pass them into the card creators:

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        // Create the JLabels
        totalAccountsLabel = new JLabel();
        savingsAccountsLabel = new JLabel();
        currentAccountsLabel = new JLabel();
        totalAmountLabel = new JLabel();

        // Pass them to the cards - now you have direct access and no cast needed!
        JPanel totalAccountsCard = createStatisticsCard("Total Accounts", "0", new Color(41, 128, 185), totalAccountsLabel);
        JPanel savingsAccountsCard = createStatisticsCard("Savings Accounts", "0", new Color(39, 174, 96), savingsAccountsLabel);
        JPanel currentAccountsCard = createStatisticsCard("Current Accounts", "0", new Color(230, 126, 34), currentAccountsLabel);
        JPanel totalAmountCard = createStatisticsCard("Total Amount in Bank", "0.00 RS", new Color(155, 89, 182), totalAmountLabel);

        // Add cards to panel
        gbc.gridx = 0; gbc.gridy = 0; panel.add(totalAccountsCard, gbc);
        gbc.gridx = 1; gbc.gridy = 0; panel.add(savingsAccountsCard, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(currentAccountsCard, gbc);
        gbc.gridx = 1; gbc.gridy = 1; panel.add(totalAmountCard, gbc);

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

    /**
     * Shows a specific sub-panel within the Bank services.
     * @param panelName The name of the panel to show (e.g., "Deposit", "FundTransfer", "OpenAccount", "CloseAccount").
     */
    public void showBankSubPanel(String panelName) {
        cardLayout.show(mainContentPanel, panelName);
    }

    /**
     * Shows the dashboard panel and updates statistics.
     */
    public void showDashboard() {
        // Update statistics
        updateStatistics();

        // Show dashboard panel
        cardLayout.show(mainContentPanel, "Dashboard");
    }

    /**
     * Updates the statistics displayed on the dashboard.
     */
    private void updateStatistics() {
        try {
            AccountStatistics stats = new AccountStatistics();

            // Get statistics
            int totalAccounts = stats.getTotalAccounts();
            int savingsAccounts = stats.getSavingsAccounts();
            int currentAccounts = stats.getCurrentAccounts();
            double totalAmount = stats.getTotalAmount();

            // Format currency
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
            String formattedAmount = currencyFormat.format(totalAmount).replace("$", "") + " RS";

            // Update labels
            totalAccountsLabel.setText(String.valueOf(totalAccounts));
            savingsAccountsLabel.setText(String.valueOf(savingsAccounts));
            currentAccountsLabel.setText(String.valueOf(currentAccounts));
            totalAmountLabel.setText(formattedAmount);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error retrieving account statistics: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Navigates back to the main Bank menu.
     */
    public void showBankMainMenu() {
        cardLayout.show(mainContentPanel, "BankMainMenu");
    }
}
