package View;

import Backend.Account;
import Model.ATMDatabase;
import Model.AccountDatabase;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {

    private JPanel mainContentPanel;
    private JPanel headerPanel;
    private CardLayout cardLayout;
    private JLabel headerTitleLabel;

    private JButton bankButton;
    private JButton atmButton;

    public MainFrame() {
        super("Haziq Bank Limited - Welcome");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 750);
        setMinimumSize(new Dimension(900, 750));
        setLocationRelativeTo(null);

        // --- Header Panel (for title/branding) ---
        headerPanel = new JPanel();
        headerPanel.setBackground(new Color(25, 118, 210)); // Professional blue
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(25, 0, 15, 0));

        headerTitleLabel = new JLabel("Haziq Bank Limited", SwingConstants.CENTER);
        headerTitleLabel.setFont(new Font("Segoe UI", Font.BOLD, 42));
        headerTitleLabel.setForeground(Color.WHITE);
        headerPanel.add(headerTitleLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        // --- Main Content Panel with CardLayout ---
        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);
        mainContentPanel.setBackground(new Color(245, 245, 245));

        // --- 1. Service Selection Panel ---
        JPanel serviceSelectionPanel = new JPanel(new GridBagLayout());
        serviceSelectionPanel.setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(30, 30, 30, 30);

        JLabel welcomeMessage = new JLabel("Welcome! Please select a service", SwingConstants.CENTER);
        welcomeMessage.setFont(new Font("Segoe UI", Font.BOLD, 30));
        welcomeMessage.setForeground(new Color(50, 50, 50));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(50, 30, 60, 30);
        serviceSelectionPanel.add(welcomeMessage, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(30, 30, 30, 30);

        gbc.gridx = 0;
        gbc.gridy = 1;
        bankButton = createStyledButton("Bank Services", new Color(76, 175, 80)); // Green for Bank
        serviceSelectionPanel.add(bankButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        atmButton = createStyledButton("ATM Services", new Color(255, 152, 0)); // Orange for ATM
        serviceSelectionPanel.add(atmButton, gbc);

        mainContentPanel.add(serviceSelectionPanel, "ServiceSelection");

        // --- 2. ATM Login Panel (NEW) ---
        JPanel atmLoginPanel = createATMLoginPanel();
        mainContentPanel.add(atmLoginPanel, "ATMLogin");

        // --- 3. ATM Panel ---
        ATMPanel atmPanel = new ATMPanel(this);
        mainContentPanel.add(atmPanel, "ATMView");

        // --- 4. Bank Panel (NEW) ---
        BankPanel bankPanel = new BankPanel(this);
        mainContentPanel.add(bankPanel, "BankView");

        add(mainContentPanel, BorderLayout.CENTER);

        // --- Footer Panel ---
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(52, 73, 94));
        footerPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        JLabel footerLabel = new JLabel("© 2025 Haziq Bank Limited. All Rights Reserved.", SwingConstants.CENTER);
        footerLabel.setForeground(Color.LIGHT_GRAY);
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        footerPanel.add(footerLabel);
        add(footerPanel, BorderLayout.SOUTH);

        // --- Action Listeners for Panel Transitions ---
        atmButton.addActionListener(e -> {
            cardLayout.show(mainContentPanel, "ATMLogin");
            headerTitleLabel.setText("ATM Login");
        });

        bankButton.addActionListener(e -> {
            cardLayout.show(mainContentPanel, "BankView");
            headerTitleLabel.setText("Bank Services");
        });

        setVisible(true);
    }

    /**
     * Helper method to create the new ATM login panel.
     */
    private JPanel createATMLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Please enter your ATM details", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(50, 50, 50));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        JLabel accountLabel = new JLabel("Account Number:", SwingConstants.RIGHT);
        accountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(accountLabel, gbc);

        JTextField accountField = new JTextField(16);
        accountField.setPreferredSize(new Dimension(250, 35));
        accountField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(accountField, gbc);

        JLabel pinLabel = new JLabel("PIN:", SwingConstants.RIGHT);
        pinLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(pinLabel, gbc);

        JPasswordField pinField = new JPasswordField(4);
        pinField.setPreferredSize(new Dimension(250, 35));
        pinField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(pinField, gbc);

        JButton loginButton = createStyledButton("Login", new Color(255, 152, 0));
        loginButton.setPreferredSize(new Dimension(120, 50));
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(30, 10, 10, 10);
        panel.add(loginButton, gbc);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        backButton.setForeground(new Color(25, 118, 210));
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 10, 10, 10);
        panel.add(backButton, gbc);

        // Action listener for the Login button
        loginButton.addActionListener(e -> {
            String accountNumber = accountField.getText().trim();
            String pin = new String(pinField.getPassword()).trim();

            try {
                if (accountNumber.isBlank() || pin.isBlank()) {
                    JOptionPane.showMessageDialog(this,
                            "Both field required",
                            "Login Failed",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (accountNumber.length() != 16) {
                    JOptionPane.showMessageDialog(this,
                            "Account number must be 16 digits",
                            "Login Failed",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (pin.length() != 4) {
                    JOptionPane.showMessageDialog(this,
                            "Account pin must be 4 digits",
                            "Login Failed",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Account account = new Account(accountNumber);
                account.setPin(pin);
                ATMDatabase atmDatabase = new ATMDatabase();
                atmDatabase.loginATM(account);

            cardLayout.show(mainContentPanel, "ATMView");
            headerTitleLabel.setText("ATM Services");
            accountField.setText("");
            pinField.setText("");
            }catch (Exception ex){
                JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
            }

    });

    // Action listener for the Back button
        backButton.addActionListener(e ->

    showServiceSelectionPanel());

        return panel;
}

/**
 * Helper method to create consistently styled buttons with enhanced hover effects.
 */
private JButton createStyledButton(String text, Color bgColor) {
    JButton button = new JButton(text);
    button.setPreferredSize(new Dimension(250, 90));
    button.setFont(new Font("Segoe UI", Font.BOLD, 26));
    button.setBackground(bgColor);
    button.setForeground(Color.WHITE);
    button.setFocusPainted(false);
    button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bgColor.darker(), 3),
            BorderFactory.createEmptyBorder(15, 25, 15, 25)
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
 * Method to navigate back to the service selection panel.
 */
public void showServiceSelectionPanel() {
    cardLayout.show(mainContentPanel, "ServiceSelection");
    headerTitleLabel.setText("Haziq Bank Limited"); // Reset header title
}

/**
 * Controls the visibility of the main header panel.
 *
 * @param visible true to show the header, false to hide it.
 */
public void setHeaderPanelVisibility(boolean visible) {
    headerPanel.setVisible(visible);
}
}