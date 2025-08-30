package View;

import Backend.Account;
import Model.ATMDatabase;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
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
     * Helper method to create the new ATM login panel with enhanced UI/UX.
     */
    private JPanel createATMLoginPanel() {
        // Main panel with a more visually appealing background
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(new Color(240, 248, 255)); // Light blue background matching ATM main menu
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Card panel to hold the login form with a card-like appearance
        JPanel cardPanel = new JPanel(new GridBagLayout());
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));

        // Add drop shadow effect to the card
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(5, 5, 5, 5),
                        BorderFactory.createLineBorder(new Color(220, 220, 220), 1)
                ),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(1, 1, 1, 1),
                        BorderFactory.createEmptyBorder(25, 35, 25, 35)
                )
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Create a stylish ATM icon header
        JPanel iconPanel = new JPanel(new BorderLayout());
        iconPanel.setBackground(new Color(25, 118, 210)); // Professional blue
        iconPanel.setPreferredSize(new Dimension(80, 80));
        iconPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Make it circular
        iconPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(25, 118, 210).darker(), 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel atmLabel = new JLabel("ATM", SwingConstants.CENTER);
        atmLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        atmLabel.setForeground(Color.WHITE);
        iconPanel.add(atmLabel, BorderLayout.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        cardPanel.add(iconPanel, gbc);

        // Title with improved styling
        JLabel titleLabel = new JLabel("ATM Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(25, 118, 210)); // Professional blue
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 12, 30, 12);
        cardPanel.add(titleLabel, gbc);

        // Account Number Field with icon and better styling
        JPanel accountPanel = new JPanel(new BorderLayout(10, 0));
        accountPanel.setBackground(Color.WHITE);

        JLabel accountIcon = new JLabel("\uD83D\uDCB3"); // Credit card emoji
        accountIcon.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        accountIcon.setHorizontalAlignment(SwingConstants.CENTER);
        accountIcon.setPreferredSize(new Dimension(40, 40));

        JPanel accountFieldPanel = new JPanel(new BorderLayout());
        accountFieldPanel.setBackground(Color.WHITE);

        JLabel accountLabel = new JLabel("Account Number");
        accountLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        accountLabel.setForeground(new Color(100, 100, 100));

        JTextField accountField = new JTextField(16);
        accountField.setPreferredSize(new Dimension(250, 40));
        accountField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        accountField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        accountFieldPanel.add(accountLabel, BorderLayout.NORTH);
        accountFieldPanel.add(accountField, BorderLayout.CENTER);

        accountPanel.add(accountIcon, BorderLayout.WEST);
        accountPanel.add(accountFieldPanel, BorderLayout.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 12, 20, 12);
        cardPanel.add(accountPanel, gbc);

        // PIN Field with icon and better styling
        JPanel pinPanel = new JPanel(new BorderLayout(10, 0));
        pinPanel.setBackground(Color.WHITE);

        JLabel pinIcon = new JLabel("\uD83D\uDD12"); // Lock emoji
        pinIcon.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        pinIcon.setHorizontalAlignment(SwingConstants.CENTER);
        pinIcon.setPreferredSize(new Dimension(40, 40));

        JPanel pinFieldPanel = new JPanel(new BorderLayout());
        pinFieldPanel.setBackground(Color.WHITE);

        JLabel pinLabel = new JLabel("PIN");
        pinLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        pinLabel.setForeground(new Color(100, 100, 100));

        JPasswordField pinField = new JPasswordField(4);
        pinField.setPreferredSize(new Dimension(250, 40));
        pinField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        pinField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        pinFieldPanel.add(pinLabel, BorderLayout.NORTH);
        pinFieldPanel.add(pinField, BorderLayout.CENTER);

        pinPanel.add(pinIcon, BorderLayout.WEST);
        pinPanel.add(pinFieldPanel, BorderLayout.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 12, 30, 12);
        cardPanel.add(pinPanel, gbc);

        // Help text for users
        JLabel helpText = new JLabel("Account number: 16 digits | PIN: 4 digits", SwingConstants.CENTER);
        helpText.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        helpText.setForeground(new Color(120, 120, 120));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 12, 20, 12);
        cardPanel.add(helpText, gbc);

        // Login Button with improved styling
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(46, 204, 113)); // Green color from ATM main menu
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(39, 174, 96), 1),
                BorderFactory.createEmptyBorder(12, 25, 12, 25)
        ));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                loginButton.setBackground(new Color(39, 174, 96));
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                loginButton.setBackground(new Color(46, 204, 113));
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets = new Insets(10, 12, 15, 12);
        cardPanel.add(loginButton, gbc);

        // Back Button with improved styling
        JButton backButton = new JButton("Back to Main Menu");
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backButton.setForeground(new Color(52, 152, 219)); // Blue from ATM main menu
        backButton.setBackground(Color.WHITE);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                backButton.setForeground(new Color(41, 128, 185));
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                backButton.setForeground(new Color(52, 152, 219));
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 12, 10, 12);
        cardPanel.add(backButton, gbc);

        // Add the card panel to the main panel
        panel.add(cardPanel, BorderLayout.CENTER);

        // Action listener for the Login button
        loginButton.addActionListener(e -> {
            String accountNumber = accountField.getText().trim();
            String pin = new String(pinField.getPassword()).trim();

            try {
                // Input validation with improved error messages
                if (accountNumber.isBlank() || pin.isBlank()) {
                    JOptionPane.showMessageDialog(this,
                            "Please enter both account number and PIN",
                            "Login Failed",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (accountNumber.length() != 16) {
                    JOptionPane.showMessageDialog(this,
                            "Account number must be exactly 16 digits",
                            "Login Failed",
                            JOptionPane.ERROR_MESSAGE);
                    accountField.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED),
                            BorderFactory.createEmptyBorder(5, 5, 5, 5)
                    ));
                    return;
                }
                if (pin.length() != 4) {
                    JOptionPane.showMessageDialog(this,
                            "PIN must be exactly 4 digits",
                            "Login Failed",
                            JOptionPane.ERROR_MESSAGE);
                    pinField.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED),
                            BorderFactory.createEmptyBorder(5, 5, 5, 5)
                    ));
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
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Action listener for the Back button
        backButton.addActionListener(e -> showServiceSelectionPanel());

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
