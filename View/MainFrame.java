package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder; // Import for EmptyBorder
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter; // Import MouseAdapter
import java.awt.event.MouseEvent; // Import MouseEvent

public class MainFrame extends JFrame {

    private JPanel mainContentPanel; // This panel will hold different views using CardLayout
    private CardLayout cardLayout; // CardLayout to manage panel transitions
    private JLabel headerTitleLabel; // Renamed to avoid confusion, holds the dynamic title

    // Declare buttons as instance variables so they can be accessed by external listeners if needed
    private JButton bankButton;
    private JButton atmButton;

    public MainFrame() {
        super("Haziq Bank Limited - Welcome"); // More precise and inviting title

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 650); // Slightly increased size for better proportions
        setMinimumSize(new Dimension(800, 600)); // Set a minimum size
        setLocationRelativeTo(null); // Center the frame

        // --- Header Panel (for title/branding) ---
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(25, 118, 210)); // A professional blue
        headerPanel.setLayout(new BorderLayout()); // Use BorderLayout for better control of title position
        headerPanel.setBorder(new EmptyBorder(25, 0, 15, 0)); // Add padding around the header content

        headerTitleLabel = new JLabel("Haziq Bank Limited", SwingConstants.CENTER); // Initialize as instance variable, center text
        headerTitleLabel.setFont(new Font("Segoe UI", Font.BOLD, 42)); // Larger, modern font for main title
        headerTitleLabel.setForeground(Color.WHITE); // White text for contrast
        headerPanel.add(headerTitleLabel, BorderLayout.CENTER); // Add to center of headerPanel

        add(headerPanel, BorderLayout.NORTH);

        // --- Main Content Panel with CardLayout ---
        cardLayout = new CardLayout(); // Initialize CardLayout
        mainContentPanel = new JPanel(cardLayout); // Assign CardLayout to mainContentPanel
        mainContentPanel.setBackground(new Color(245, 245, 245)); // Very light gray for clean separation

        // --- 1. Service Selection Panel ---
        JPanel serviceSelectionPanel = new JPanel(new GridBagLayout());
        serviceSelectionPanel.setBackground(new Color(245, 245, 245)); // Same background as mainContentPanel

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(30, 30, 30, 30); // Generous spacing between components

        // Welcome Message / Instruction
        JLabel welcomeMessage = new JLabel("Welcome! Please select a service:", SwingConstants.CENTER);
        welcomeMessage.setFont(new Font("Segoe UI", Font.BOLD, 30)); // Prominent welcome message
        welcomeMessage.setForeground(new Color(50, 50, 50)); // Dark gray for readability
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span across two columns
        gbc.insets = new Insets(50, 30, 60, 30); // More padding above/below welcome message
        serviceSelectionPanel.add(welcomeMessage, gbc);

        // Reset gridwidth and insets for buttons
        gbc.gridwidth = 1;
        gbc.insets = new Insets(30, 30, 30, 30); // Reset button insets

        gbc.gridx = 0;
        gbc.gridy = 1;
        bankButton = createStyledButton("Bank Services", new Color(76, 175, 80)); // Green for Bank
        serviceSelectionPanel.add(bankButton, gbc);

        gbc.gridx = 1;
        atmButton = createStyledButton("ATM Services", new Color(255, 152, 0)); // Orange for ATM
        serviceSelectionPanel.add(atmButton, gbc);

        // Add the initial service selection panel to mainContentPanel with a unique name
        mainContentPanel.add(serviceSelectionPanel, "ServiceSelection");

        // --- 2. ATM Panel ---
        ATMPanel atmPanel = new ATMPanel(this); // Create the ATM panel instance
        mainContentPanel.add(atmPanel, "ATMView"); // Add it to CardLayout with a name

        // Add mainContentPanel to the frame's center
        add(mainContentPanel, BorderLayout.CENTER);

        // --- Footer Panel ---
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(52, 73, 94)); // Darker, professional color
        footerPanel.setBorder(new EmptyBorder(10, 0, 10, 0)); // Add some vertical padding
        JLabel footerLabel = new JLabel("© 2025 Haziq Bank Limited. All Rights Reserved.", SwingConstants.CENTER);
        footerLabel.setForeground(Color.LIGHT_GRAY);
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13)); // Slightly larger footer font
        footerPanel.add(footerLabel);
        add(footerPanel, BorderLayout.SOUTH);

        // --- Action Listeners for Panel Transitions ---
        atmButton.addActionListener(e -> {
            cardLayout.show(mainContentPanel, "ATMView"); // Show the ATM panel
            headerTitleLabel.setText("ATM Services"); // Update header title
        });

        bankButton.addActionListener(e -> {
            // In a real application, you would add a specific "BankView" panel here
            // For now, let's just show a message.
            headerTitleLabel.setText("Bank Services"); // Update header title for consistency
            JOptionPane.showMessageDialog(this, "Bank services are currently under development. Please check back later!", "Information", JOptionPane.INFORMATION_MESSAGE);
            // Optionally, switch back to ServiceSelection if no BankView is shown.
            // cardLayout.show(mainContentPanel, "ServiceSelection");
            // headerTitleLabel.setText("Haziq Bank Limited");
        });

        setVisible(true);
    }

    /**
     * Helper method to create consistently styled buttons with enhanced hover effects.
     */
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(250, 90)); // Even larger buttons for impact
        button.setFont(new Font("Segoe UI", Font.BOLD, 26)); // Modern, larger font
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE); // White text
        button.setFocusPainted(false); // Remove focus border
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker(), 3), // Thicker, darker border
                BorderFactory.createEmptyBorder(15, 25, 15, 25) // More inner padding
        ));
        // Add a subtle hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(bgColor.brighter());
                button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand on hover
            }

            @Override
            public void mouseExited(MouseEvent evt) { // Corrected method name: mouseExited (lowercase 'm')
                button.setBackground(bgColor);
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Revert cursor on exit
            }
        });
        return button;
    }

    /**
     * Method to navigate back to the service selection panel.
     * This can be called from other panels (like ATMPanel) when a "Back" button is pressed.
     */
    public void showServiceSelectionPanel() {
        cardLayout.show(mainContentPanel, "ServiceSelection");
        headerTitleLabel.setText("Haziq Bank Limited"); // Reset header title
    }
}