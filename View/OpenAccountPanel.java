package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OpenAccountPanel extends JPanel {

    private BankPanel parentBankPanel;

    public OpenAccountPanel(BankPanel parentBankPanel) {
        this.parentBankPanel = parentBankPanel;
        setLayout(new BorderLayout(0, 30));
        setBackground(new Color(230, 240, 250)); // Consistent light bluish background

        // --- Title Label ---
        JLabel titleLabel = new JLabel("Open New Account", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 38));
        titleLabel.setForeground(new Color(39, 174, 96)); // Green color for Open Account theme
        titleLabel.setBorder(new EmptyBorder(25, 0, 15, 0));
        add(titleLabel, BorderLayout.NORTH);

        // --- Input Area ---
        JPanel inputContainerPanel = new JPanel(new GridBagLayout());
        inputContainerPanel.setBackground(new Color(255, 255, 255)); // White background
        inputContainerPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(195, 250, 195), 3, true), // Softer green border, rounded
            new EmptyBorder(30, 50, 30, 50) // Generous internal padding
        ));
        inputContainerPanel.setPreferredSize(new Dimension(850, 500));
        inputContainerPanel.setMaximumSize(new Dimension(850, 500));
        inputContainerPanel.setMinimumSize(new Dimension(750, 450));

        JPanel centerWrapperPanel = new JPanel(new GridBagLayout());
        centerWrapperPanel.setBackground(getBackground());
        centerWrapperPanel.add(inputContainerPanel);
        add(centerWrapperPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Spacing for input fields
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Make components fill horizontally
        gbc.weightx = 1.0; // Give extra weight to horizontal expansion

        // --- Account Type ---
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel accountTypeLabel = new JLabel("Account Type:");
        accountTypeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        accountTypeLabel.setForeground(new Color(80, 80, 80));
        inputContainerPanel.add(accountTypeLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        JRadioButton savingsRadio = new JRadioButton("Savings");
        JRadioButton currentRadio = new JRadioButton("Current");
        ButtonGroup accountTypeGroup = new ButtonGroup();
        accountTypeGroup.add(savingsRadio);
        accountTypeGroup.add(currentRadio);
        savingsRadio.setSelected(true); // Default selection
        savingsRadio.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        currentRadio.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        savingsRadio.setBackground(inputContainerPanel.getBackground());
        currentRadio.setBackground(inputContainerPanel.getBackground());
        
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        radioPanel.setBackground(inputContainerPanel.getBackground());
        radioPanel.add(savingsRadio);
        radioPanel.add(currentRadio);
        inputContainerPanel.add(radioPanel, gbc);

        // --- Customer Details ---
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        nameLabel.setForeground(new Color(80, 80, 80));
        inputContainerPanel.add(nameLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        JTextField nameField = createStyledTextField();
        inputContainerPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        JLabel cnicLabel = new JLabel("CNIC Number:");
        cnicLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        cnicLabel.setForeground(new Color(80, 80, 80));
        inputContainerPanel.add(cnicLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        JTextField cnicField = createStyledTextField();
        inputContainerPanel.add(cnicField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        phoneLabel.setForeground(new Color(80, 80, 80));
        inputContainerPanel.add(phoneLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        JTextField phoneField = createStyledTextField();
        inputContainerPanel.add(phoneField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        addressLabel.setForeground(new Color(80, 80, 80));
        inputContainerPanel.add(addressLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 4;
        JTextArea addressArea = new JTextArea(4, 35); // Reduced rows and columns
        addressArea.setFont(new Font("Consolas", Font.PLAIN, 18));
        addressArea.setForeground(Color.BLACK);
        addressArea.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(180, 180, 180), 1),
            new EmptyBorder(8, 12, 8, 12) // Reduced padding
        ));
        addressArea.setLineWrap(true);
        addressArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(addressArea);
        scrollPane.setPreferredSize(new Dimension(400, 90)); // Reduced width and height
        scrollPane.setMinimumSize(new Dimension(350, 80)); // Reduced minimum size
        inputContainerPanel.add(scrollPane, gbc);

        // --- Submit Button ---
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(25, 10, 15, 10); // Reduced top padding for button
        gbc.fill = GridBagConstraints.NONE; // Don't fill for button
        JButton submitButton = createStyledButton("Open Account", new Color(39, 174, 96));
        inputContainerPanel.add(submitButton, gbc);

        // --- Back Button ---
        JButton backButton = createStyledButton("Back", new Color(96, 125, 139));
        backButton.addActionListener(e -> parentBankPanel.showBankMainMenu());

        JPanel bottomButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 25));
        bottomButtonPanel.setBackground(getBackground());
        bottomButtonPanel.add(backButton);
        add(bottomButtonPanel, BorderLayout.SOUTH);

        // --- Action Listener for Submit Button ---
        submitButton.addActionListener(e -> {
            String accType = savingsRadio.isSelected() ? "Savings" : "Current";
            String name = nameField.getText().trim();
            String cnic = cnicField.getText().trim();
            String phone = phoneField.getText().trim();
            String address = addressArea.getText().trim();

            if (name.isEmpty() || cnic.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required to open an account.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Basic validation for CNIC/Phone (can be expanded)
            if (!cnic.matches("\\d{13}")) { // Assuming 13-digit CNIC
                JOptionPane.showMessageDialog(this, "CNIC should be 13 digits (numbers only).", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!phone.matches("\\d{11}")) { // Assuming 11-digit phone number
                JOptionPane.showMessageDialog(this, "Phone number should be 11 digits (numbers only).", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // In a real application, you would create an account object and save it
            String message = String.format("Account Type: %s%nName: %s%nCNIC: %s%nPhone: %s%nAddress: %s%n%nAccount opening request submitted!",
                                            accType, name, cnic, phone, address);
            JOptionPane.showMessageDialog(this, message, "Account Opening Confirmation", JOptionPane.INFORMATION_MESSAGE);

            // Clear fields after submission
            nameField.setText("");
            cnicField.setText("");
            phoneField.setText("");
            addressArea.setText("");
            savingsRadio.setSelected(true); // Reset to default

            parentBankPanel.showBankMainMenu(); // Go back to Bank menu
        });
    }

    /**
     * Helper method to create consistently styled JTextFields.
     */
    private JTextField createStyledTextField() {
        JTextField field = new JTextField(35); // Increased to 35 characters
        field.setFont(new Font("Consolas", Font.PLAIN, 20));
        field.setForeground(Color.BLACK);
        field.setPreferredSize(new Dimension(450, 50)); // Much larger width and height
        field.setMinimumSize(new Dimension(400, 45)); // Set minimum size
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(180, 180, 180), 1),
            new EmptyBorder(10, 15, 10, 15) // Increased padding
        ));
        return field;
    }

    /**
     * Helper method to create consistently styled buttons.
     */
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(180, 45)); // Reduced size
        button.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Reduced font size
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker(), 2),
                BorderFactory.createEmptyBorder(8, 16, 8, 16) // Reduced padding
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