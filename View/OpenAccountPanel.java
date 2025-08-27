package View;

import Backend.Account;
import Model.AccountDatabase;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class OpenAccountPanel extends JPanel {

    private BankPanel parentBankPanel;

    public OpenAccountPanel(BankPanel parentBankPanel) {
        this.parentBankPanel = parentBankPanel;
        setLayout(new BorderLayout(0, 30));
        setBackground(new Color(230, 240, 250)); // Consistent light bluish background


        // --- Input Area ---
        JPanel inputContainerPanel = new JPanel(new GridBagLayout());
        inputContainerPanel.setBackground(new Color(255, 255, 255)); // White background
        inputContainerPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(195, 250, 195), 2, true), // Softer green border, rounded
            new EmptyBorder(20, 30, 20, 30) // Reduced internal padding
        ));
        inputContainerPanel.setPreferredSize(new Dimension(750, 480));
        inputContainerPanel.setMaximumSize(new Dimension(800, 500));
        inputContainerPanel.setMinimumSize(new Dimension(650, 400));

        JPanel centerWrapperPanel = new JPanel(new GridBagLayout());
        centerWrapperPanel.setBackground(getBackground());
        centerWrapperPanel.add(inputContainerPanel);
        add(centerWrapperPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8); // Reduced spacing for input fields
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Make components fill horizontally
        gbc.weightx = 1.0; // Give extra weight to horizontal expansion

        // --- Account Type ---
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel accountTypeLabel = new JLabel("Account Type");
        accountTypeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        accountTypeLabel.setForeground(new Color(80, 80, 80));
        inputContainerPanel.add(accountTypeLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        JRadioButton savingsRadio = new JRadioButton("Savings");
        JRadioButton currentRadio = new JRadioButton("Current");
        ButtonGroup accountTypeGroup = new ButtonGroup();
        accountTypeGroup.add(savingsRadio);
        accountTypeGroup.add(currentRadio);
        savingsRadio.setSelected(true); // Default selection
        savingsRadio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        currentRadio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        savingsRadio.setBackground(inputContainerPanel.getBackground());
        currentRadio.setBackground(inputContainerPanel.getBackground());

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        radioPanel.setBackground(inputContainerPanel.getBackground());
        radioPanel.add(savingsRadio);
        radioPanel.add(currentRadio);
        inputContainerPanel.add(radioPanel, gbc);

        // --- Customer Details ---
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel nameLabel = new JLabel("Full Name");
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        nameLabel.setForeground(new Color(80, 80, 80));
        inputContainerPanel.add(nameLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        JTextField nameField = createStyledTextField();
        inputContainerPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        JLabel cnicLabel = new JLabel("CNIC Number:");
        cnicLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        cnicLabel.setForeground(new Color(80, 80, 80));
        inputContainerPanel.add(cnicLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        JTextField cnicField = createStyledTextField();
        inputContainerPanel.add(cnicField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        phoneLabel.setForeground(new Color(80, 80, 80));
        inputContainerPanel.add(phoneLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        JTextField phoneField = createStyledTextField();
        inputContainerPanel.add(phoneField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        JLabel addressLabel = new JLabel("Address");
        addressLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        addressLabel.setForeground(new Color(80, 80, 80));
        inputContainerPanel.add(addressLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 4;
        JTextArea addressArea = new JTextArea(3, 25); // Further reduced rows and columns
        addressArea.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Reduced font size
        addressArea.setForeground(Color.BLACK);
        addressArea.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(180, 180, 180), 1),
            new EmptyBorder(6, 10, 6, 10) // Further reduced padding
        ));
        addressArea.setLineWrap(true);
        addressArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(addressArea);
        scrollPane.setPreferredSize(new Dimension(350, 70)); // Further reduced width and height
        scrollPane.setMinimumSize(new Dimension(300, 60)); // Further reduced minimum size
        inputContainerPanel.add(scrollPane, gbc);

        // --- ATM Card Option ---
        gbc.gridx = 0; gbc.gridy = 5;
        JLabel atmCardLabel = new JLabel("Issue ATM Card");
        atmCardLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        atmCardLabel.setForeground(new Color(80, 80, 80));
        inputContainerPanel.add(atmCardLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 5;
        JRadioButton atmYesRadio = new JRadioButton("Yes");
        JRadioButton atmNoRadio = new JRadioButton("No");
        ButtonGroup atmCardGroup = new ButtonGroup();
        atmCardGroup.add(atmYesRadio);
        atmCardGroup.add(atmNoRadio);
        atmNoRadio.setSelected(true); // Default selection is No
        atmYesRadio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        atmNoRadio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        atmYesRadio.setBackground(inputContainerPanel.getBackground());
        atmNoRadio.setBackground(inputContainerPanel.getBackground());

        JPanel atmRadioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        atmRadioPanel.setBackground(inputContainerPanel.getBackground());
        atmRadioPanel.add(atmYesRadio);
        atmRadioPanel.add(atmNoRadio);
        inputContainerPanel.add(atmRadioPanel, gbc);

        // --- PIN Field (initially hidden) ---
        gbc.gridx = 0; gbc.gridy = 6;
        JLabel pinLabel = new JLabel("4-Digit PIN");
        pinLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        pinLabel.setForeground(new Color(80, 80, 80));
        pinLabel.setVisible(false); // Initially hidden
        inputContainerPanel.add(pinLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 6;
        JPasswordField pinField = new JPasswordField(4);
        pinField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pinField.setForeground(Color.BLACK);
        pinField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(180, 180, 180), 1),
            new EmptyBorder(8, 12, 8, 12)
        ));
        pinField.setVisible(false); // Initially hidden
        inputContainerPanel.add(pinField, gbc);

        // Add listeners to show/hide PIN field based on radio button selection
        atmYesRadio.addActionListener(e -> {
            pinLabel.setVisible(true);
            pinField.setVisible(true);
        });

        atmNoRadio.addActionListener(e -> {
            pinLabel.setVisible(false);
            pinField.setVisible(false);
            pinField.setText(""); // Clear PIN when hidden
        });

        // --- Submit Button ---
        gbc.gridx = 0; gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(15, 8, 10, 8); // Further reduced padding for button
        gbc.fill = GridBagConstraints.NONE; // Don't fill for button
        JButton submitButton = createStyledButton("Open Account", new Color(39, 174, 96));
        inputContainerPanel.add(submitButton, gbc);

        // --- Action Listener for Submit Button ---
        submitButton.addActionListener(e -> {
            byte accType = 0;
            if (savingsRadio.getText().equals("Current")) accType = 1;
            String name = nameField.getText().trim();
            String cnic = cnicField.getText().trim();
            String phone = phoneField.getText().trim();
            String address = addressArea.getText().trim();
            boolean issueATMCard = atmYesRadio.isSelected();
            String pin = "";

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

            // Validate PIN if ATM card is selected
            if (issueATMCard) {
                pin = new String(pinField.getPassword()).trim();
                if (pin.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "PIN is required when issuing an ATM card.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!pin.matches("\\d{4}")) {
                    JOptionPane.showMessageDialog(this, "PIN should be exactly 4 digits (numbers only).", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            try {
                AccountDatabase account = new AccountDatabase();
                // Note: You may need to update the Account constructor or add parameters to include ATM card and PIN


                StringBuilder messageBuilder = new StringBuilder();
                messageBuilder.append(String.format("Account Type: %s%nName: %s%nCNIC: %s%nPhone: %s%nAddress: %s%n", 
                        accType == 0 ? "Savings" : "Current", name, cnic, phone, address));

                if (issueATMCard) {
                    account.createAccount(new Account(name, cnic, phone, accType, address, pin));
                    messageBuilder.append(String.format("ATM Card: Yes%nPIN: %s%n", pin));
                } else {
                    account.createAccount(new Account(name, cnic, phone, accType, address));
                    messageBuilder.append("ATM Card: No\n");
                }

                messageBuilder.append("\nAccount opening request submitted!");

                JOptionPane.showMessageDialog(this, messageBuilder.toString(), "Account Opening Confirmation", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException exception) {
                JOptionPane.showMessageDialog(this, exception, "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Clear fields after submission
            nameField.setText("");
            cnicField.setText("");
            phoneField.setText("");
            addressArea.setText("");
            pinField.setText("");
            savingsRadio.setSelected(true); // Reset to default
            atmNoRadio.setSelected(true); // Reset ATM card option
            pinLabel.setVisible(false); // Hide PIN field
            pinField.setVisible(false);

            parentBankPanel.showBankMainMenu(); // Go back to Bank menu
        });
    }

    /**
     * Helper method to create consistently styled JTextFields.
     */
    private JTextField createStyledTextField() {
        JTextField field = new JTextField(25); // Reduced to 25 characters
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Reduced font size
        field.setForeground(Color.BLACK);
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(180, 180, 180), 1),
            new EmptyBorder(8, 12, 8, 12) // Reduced padding
        ));
        return field;
    }

    /**
     * Helper method to create consistently styled buttons.
     */
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(160, 40)); // Further reduced size
        button.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Further reduced font size
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker(), 1), // Thinner border
                BorderFactory.createEmptyBorder(6, 12, 6, 12) // Further reduced padding
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
