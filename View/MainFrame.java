package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter; // Import MouseAdapter
import java.awt.event.MouseEvent; // Import MouseEvent

public class MainFrame extends JFrame {

    private JPanel mainContentPanel; // This panel will hold different views using CardLayout
    private CardLayout cardLayout; // CardLayout to manage panel transitions
    private JLabel titleLabel; // Make titleLabel an instance variable to update it

    // Declare buttons as instance variables so they can be accessed by external listeners if needed
    private JButton bankButton;
    private JButton atmButton;

    public MainFrame() {
        super("Welcome to Haziq Bank Limited"); // More inviting title

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Center the frame

        // --- Header Panel (for title/branding) ---
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(25, 118, 210)); // A more professional blue
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20)); // Center content vertically
        titleLabel = new JLabel("Haziq Bank Limited"); // Initialize as instance variable
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36)); // Modern, larger font
        titleLabel.setForeground(Color.WHITE); // White text for contrast
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // --- Main Content Panel with CardLayout ---
        cardLayout = new CardLayout(); // Initialize CardLayout
        mainContentPanel = new JPanel(cardLayout); // Assign CardLayout to mainContentPanel
        mainContentPanel.setBackground(new Color(240, 245, 249)); // Light background

        // --- 1. Service Selection Panel ---
        JPanel serviceSelectionPanel = new JPanel(new GridBagLayout());
        serviceSelectionPanel.setBackground(new Color(240, 245, 249)); // Same background
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(30, 30, 30, 30);
        gbc.gridx = 0;
        gbc.gridy = 0;

        bankButton = createStyledButton("Bank", new Color(76, 175, 80));
        serviceSelectionPanel.add(bankButton, gbc);

        gbc.gridx = 1;
        atmButton = createStyledButton("ATM", new Color(255, 152, 0));
        serviceSelectionPanel.add(atmButton, gbc);

        // Add the initial service selection panel to mainContentPanel with a unique name
        mainContentPanel.add(serviceSelectionPanel, "ServiceSelection");

        // --- 2. ATM Panel ---
        ATMPanel atmPanel = new ATMPanel(this); // Create the ATM panel instance
        mainContentPanel.add(atmPanel, "ATMView"); // Add it to CardLayout with a name

        // Add mainContentPanel to the frame's center
        add(mainContentPanel, BorderLayout.CENTER);

        // --- Footer Panel (optional, for branding or info) ---
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(52, 73, 94)); // Darker, professional color
        JLabel footerLabel = new JLabel("© 2025 Haziq Bank Limited. All Rights Reserved."); // Updated footer
        footerLabel.setForeground(Color.LIGHT_GRAY);
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footerPanel.add(footerLabel);
        add(footerPanel, BorderLayout.SOUTH);

        // --- Action Listeners for Panel Transitions ---
        atmButton.addActionListener(e -> {
            cardLayout.show(mainContentPanel, "ATMView"); // Show the ATM panel
            titleLabel.setText("ATM Services"); // Update header title
        });

        bankButton.addActionListener(e -> {
            // Here you would add the panel for "Bank" services, e.g., "BankView"
            // For now, let's just show a message.
            // cardLayout.show(mainContentPanel, "BankView");
            titleLabel.setText("Bank Services"); // Update header title
            JOptionPane.showMessageDialog(this, "Bank services are currently under development.", "Information", JOptionPane.INFORMATION_MESSAGE);
            // After showing the message, you might want to switch back to ServiceSelection if no BankView is shown.
            // cardLayout.show(mainContentPanel, "ServiceSelection");
            // titleLabel.setText("Haziq Bank Limited");
        });

        setVisible(true);
    }

    /**
     * Helper method to create consistently styled buttons.
     */
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 70)); // Larger buttons
        button.setFont(new Font("Segoe UI", Font.BOLD, 24)); // Modern, larger font
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE); // White text
        button.setFocusPainted(false); // Remove focus border
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker(), 2), // Slightly darker border
                BorderFactory.createEmptyBorder(10, 20, 10, 20) // Inner padding
        ));
        // Add a subtle hover effect (optional, requires a MouseListener)
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }

            public void MouseExited(MouseEvent evt) { // Corrected method name: MouseExited
                button.setBackground(bgColor);
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
        titleLabel.setText("Haziq Bank Limited"); // Reset header title
    }
}