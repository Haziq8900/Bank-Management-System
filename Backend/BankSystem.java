package Backend;

import java.sql.*;
import java.util.Random;

import javax.swing.JOptionPane;
import Backend.Account;

public class BankSystem {
    /**
     * Closes an account if the PIN is correct and the balance is zero.
     * @param accountNumber The account number to close.
     * @param pin The PIN for verification.
     * @return true if account closed, false otherwise.
     */
    public boolean closeAccount(long accountNumber, int pin) {
        String selectSQL = "SELECT balance, pin FROM accounts WHERE account_number = ?";
        String deleteSQL = "DELETE FROM accounts WHERE account_number = ?";
        try (Connection conn = getConnection();
            PreparedStatement selectStmt = conn.prepareStatement(selectSQL)) {
            selectStmt.setLong(1, accountNumber);
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                double balance = rs.getDouble("balance");
                int storedPin = rs.getInt("pin");
                if (storedPin != pin) {
                    JOptionPane.showMessageDialog(null, "Incorrect PIN. Account closure denied.", "PIN Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                if (balance > 0) {
                    JOptionPane.showMessageDialog(null, "Account balance must be zero to close the account.", "Closure Error", JOptionPane.WARNING_MESSAGE);
                    return false;
                }
                // Proceed to delete
                try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSQL)) {
                    deleteStmt.setLong(1, accountNumber);
                    int deleted = deleteStmt.executeUpdate();
                    if (deleted > 0) {
                        JOptionPane.showMessageDialog(null, "Account " + accountNumber + " successfully closed.", "Account Closed", JOptionPane.INFORMATION_MESSAGE);
                        return true;
                    } else {
                        JOptionPane.showMessageDialog(null, "Account closure failed.", "Closure Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Account not found.", "Closure Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Closure Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/ATM_DB", "root", "HaziqKhan@30-01-2005");
    }

    public long openAccount() {
    try (Connection conn = getConnection()) {

        // --- Step 1: Get and validate customer info ---
        String name = JOptionPane.showInputDialog(null, "Enter your full name:");
        if (name == null || name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Name is required.");
            return -1;
        }

        String cnic = JOptionPane.showInputDialog(null, "Enter your 13-digit CNIC (no dashes):");
        if (cnic == null || !cnic.matches("\\d{13}")) {
            JOptionPane.showMessageDialog(null, "Invalid CNIC.");
            return -1;
        }

        String phone = JOptionPane.showInputDialog(null, "Enter your 11-digit phone number:");
        if (phone == null || !phone.matches("\\d{11}")) {
            JOptionPane.showMessageDialog(null, "Invalid phone number.");
            return -1;
        }

        String address = JOptionPane.showInputDialog(null, "Enter your address:");
        if (address == null || address.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Address is required.");
            return -1;
        }

        // --- Step 2: Ask account type ---
        String[] options = {"Savings", "Current"};
        String accountType = (String) JOptionPane.showInputDialog(null, "Select account type:", "Account Type",
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (accountType == null) {
            JOptionPane.showMessageDialog(null, "Account type selection cancelled.");
            return -1;
        }

        // --- Step 3: Set 4-digit PIN ---
        String pinInput = JOptionPane.showInputDialog(null, "Set a 4-digit PIN for your new account:");
        if (pinInput == null || !pinInput.matches("\\d{4}")) {
            JOptionPane.showMessageDialog(null, "Invalid PIN. Account not created.");
            return -1;
        }
        int pin = Integer.parseInt(pinInput);

        // --- Step 4: Insert customer into DB ---
        String insertCustomerSQL = "INSERT INTO customers (name, cnic, phone, address) VALUES (?, ?, ?, ?)";
        PreparedStatement customerStmt = conn.prepareStatement(insertCustomerSQL, Statement.RETURN_GENERATED_KEYS);
        customerStmt.setString(1, name.trim());
        customerStmt.setString(2, cnic);
        customerStmt.setString(3, phone);
        customerStmt.setString(4, address.trim());

        int customerInserted = customerStmt.executeUpdate();
        if (customerInserted == 0) {
            JOptionPane.showMessageDialog(null, "Customer creation failed.");
            return -1;
        }

        ResultSet generatedKeys = customerStmt.getGeneratedKeys();
        int customerId = -1;
        if (generatedKeys.next()) {
            customerId = generatedKeys.getInt(1);
        } else {
            JOptionPane.showMessageDialog(null, "Failed to retrieve customer ID.");
            return -1;
        }

        // --- Step 5: Create Account ---
        long accountNumber = generateUniqueAccountNumber(conn); // Assume this method exists
        String insertAccountSQL = "INSERT INTO accounts (customer_id, account_number, account_type, balance, pin) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement accountStmt = conn.prepareStatement(insertAccountSQL);
        accountStmt.setInt(1, customerId);
        accountStmt.setLong(2, accountNumber);
        accountStmt.setString(3, accountType);
        accountStmt.setDouble(4, 1000.0); // default balance
        accountStmt.setInt(5, pin);

        int accountInserted = accountStmt.executeUpdate();
        if (accountInserted > 0) {
            JOptionPane.showMessageDialog(null, "Account created successfully!\n\nAccount Number: " + accountNumber + "\nDefault Balance: 1000RS\nPlease remember your PIN.");
            return accountNumber;
        } else {
            JOptionPane.showMessageDialog(null, "Account creation failed.");
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
    }

    return -1;
}

    // Generates a unique 16-digit account number
    private long generateUniqueAccountNumber(Connection conn) throws SQLException {
        Random random = new Random();
        long accountNumber;

        String checkSQL = "SELECT account_number FROM accounts WHERE account_number = ?";

        do {
            accountNumber = 1_0000_0000_0000_0000L + (long)(random.nextDouble() * 8_9999_9999_9999_9999L);

            try (PreparedStatement checkStmt = conn.prepareStatement(checkSQL)) {
                checkStmt.setLong(1, accountNumber);
                ResultSet rs = checkStmt.executeQuery();
                if (!rs.next()) {
                    break; // Unique
                }
            }

        } while (true);

        return accountNumber;
    }

    public boolean depositFunds(long accountNumber, double amount) {
        String selectSQL = "SELECT balance FROM accounts WHERE account_number = ?";
        String updateSQL = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";

    try (Connection conn = getConnection();
        PreparedStatement selectStmt = conn.prepareStatement(selectSQL);
        PreparedStatement updateStmt = conn.prepareStatement(updateSQL)) {

        // Check if account exists
        selectStmt.setLong(1, accountNumber);
        ResultSet rs = selectStmt.executeQuery();

        if (rs.next()) {
            // Account exists, proceed to deposit
            updateStmt.setDouble(1, amount);
            updateStmt.setLong(2, accountNumber);
            int rowsUpdated = updateStmt.executeUpdate();

        if (rowsUpdated > 0) {
            System.out.println("Successfully deposited " + amount + "RS into account " + accountNumber);
            return true;
        }
        } else {
            System.out.println("Account not found.");
        }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean transferFunds(long senderAccountNumber, long receiverAccountNumber, double amount) {
        // Prompt for sender's PIN
        String enteredPin = JOptionPane.showInputDialog(null, "Enter PIN for sender's account:", "PIN Verification", JOptionPane.PLAIN_MESSAGE);

        if (enteredPin == null || enteredPin.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "PIN is required to proceed with the transfer.", "Transfer Cancelled", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // SQL statements
        String senderQuery = "SELECT balance, pin FROM accounts WHERE account_number = ?";
        String receiverQuery = "SELECT balance FROM accounts WHERE account_number = ?";
        String updateSender = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
        String updateReceiver = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";

        try (Connection conn = getConnection()) {
            // Step 1: Validate sender account, get balance and PIN
            PreparedStatement senderStmt = conn.prepareStatement(senderQuery);
            senderStmt.setLong(1, senderAccountNumber);
            ResultSet senderResult = senderStmt.executeQuery();

        if (!senderResult.next()) {
            JOptionPane.showMessageDialog(null, "Sender account does not exist.", "Transfer Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        double senderBalance = senderResult.getDouble("balance");
        String storedPin = senderResult.getString("pin");

        // Step 2: Check PIN
        if (!enteredPin.equals(storedPin)) {
            JOptionPane.showMessageDialog(null, "Incorrect PIN. Transfer denied.", "PIN Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Step 3: Validate receiver account
        PreparedStatement receiverStmt = conn.prepareStatement(receiverQuery);
        receiverStmt.setLong(1, receiverAccountNumber);
        ResultSet receiverResult = receiverStmt.executeQuery();

        if (!receiverResult.next()) {
            JOptionPane.showMessageDialog(null, "Receiver account does not exist.", "Transfer Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Step 4: Check sender's balance
        if (senderBalance < amount) {
            JOptionPane.showMessageDialog(null, "Insufficient funds in sender's account.", "Transfer Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Step 5: Perform transfer in a transaction
        conn.setAutoCommit(false);

        PreparedStatement updateSenderStmt = conn.prepareStatement(updateSender);
        updateSenderStmt.setDouble(1, amount);
        updateSenderStmt.setLong(2, senderAccountNumber);
        int senderUpdated = updateSenderStmt.executeUpdate();

        PreparedStatement updateReceiverStmt = conn.prepareStatement(updateReceiver);
        updateReceiverStmt.setDouble(1, amount);
        updateReceiverStmt.setLong(2, receiverAccountNumber);
        int receiverUpdated = updateReceiverStmt.executeUpdate();

        if (senderUpdated > 0 && receiverUpdated > 0) {
            conn.commit();
            JOptionPane.showMessageDialog(null, "Funds transferred successfully!\n" +
                    "Transferred Amount: ₹" + amount + "\nSender's New Balance: ₹" + (senderBalance - amount));
            return true;
        } else {
            conn.rollback();
            JOptionPane.showMessageDialog(null, "Transfer failed. Please try again.", "Transfer Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Transfer Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                getConnection().setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
