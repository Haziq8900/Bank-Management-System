package View;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        // Set the title of the frame
        super("Bank/ATM Interface");

        // Set the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the size of the frame
        setSize(800, 600); // A good starting size

        // Center the frame on the screen
        setLocationRelativeTo(null);

        // Create a JPanel to hold the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.LIGHT_GRAY); // A light background for the panel
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Center buttons with some spacing

        // Create the "Bank" button
        JButton bankButton = new JButton("Bank");
        bankButton.setPreferredSize(new Dimension(150, 50)); // Set preferred size
        bankButton.setFont(new Font("Arial", Font.BOLD, 20)); // Set font
        // You can add an ActionListener here later for bank functionality
        // bankButton.addActionListener(e -> { /* Bank logic here */ });

        // Create the "ATM" button
        JButton atmButton = new JButton("ATM");
        atmButton.setPreferredSize(new Dimension(150, 50)); // Set preferred size
        atmButton.setFont(new Font("Arial", Font.BOLD, 20)); // Set font
        // You can add an ActionListener here later for ATM functionality
        // atmButton.addActionListener(e -> { /* ATM logic here */ });

        // Add buttons to the panel
        buttonPanel.add(bankButton);
        buttonPanel.add(atmButton);

        // Add the button panel to the frame's content pane
        add(buttonPanel, BorderLayout.CENTER);

        // Make the frame visible
        setVisible(true);
    }

    public static void main(String[] args) {
        // Create the MainFrame on the Event Dispatch Thread (EDT) for thread safety
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}