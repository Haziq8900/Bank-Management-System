package Backend;

import java.sql.*;
import java.util.Random;
import javax.swing.JOptionPane;

public class ATMSystem {

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/ATM_DB"; // your DB name
        String user = "root"; // your MySQL username
        String password = "HaziqKhan@30-01-2005"; // your MySQL password (empty if none)
        return DriverManager.getConnection(url, user, password);
    }

    private long currentLoggedInAccount = -1; //-1 means no one logged in

    public boolean login(long accountNumber, int pin) {
        String sql = "SELECT * FROM accounts WHERE account_number = ? and pin = ?";

        try (Connection conn = getConnection();
            PreparedStatement psmt = conn.prepareStatement(sql)) {
                psmt.setLong(1, accountNumber);
                psmt.setInt(2, pin);

                ResultSet rs = psmt.executeQuery();
                if(rs.next()) {
                    currentLoggedInAccount = accountNumber; // Set the logged-in account
                    JOptionPane.showMessageDialog(null,
                    "Login successfull! Welcome to Haziq Bank ATM Services",
                    "Success",JOptionPane.INFORMATION_MESSAGE);
                    return true;
                } else{
                    JOptionPane.showMessageDialog(null,
                    "Login denied, please check your account number and pin",
                    "Login Failed",JOptionPane.WARNING_MESSAGE);
                    return false;
                }
            }
            catch (SQLException e){
                JOptionPane.showMessageDialog(null,
                "Database error: "+e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

    public boolean cashWithdrawal(double amount) {
        if (currentLoggedInAccount == -1) {
            JOptionPane.showMessageDialog(null,
                "No account is logged in. Please log in first.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (amount < 1000) {
            JOptionPane.showMessageDialog(null,
            "Please enter a amount greater or equal to 1000RS.",
            "Invalid amount", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        String checkBalanceSQL = "SELECT balance FROM accounts WHERE account_number = ?";
        String updateBalanceSQL = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
        String insertTransactionSQL = "INSERT INTO transactions (account_number, type, amount, date_time) VALUES (?, ?, ?, NOW())";

        Connection conn = null;

        try {
            conn = getConnection();
            conn.setAutoCommit(false); // Start transaction

            try (PreparedStatement checkStmt = conn.prepareStatement(checkBalanceSQL)) {
                checkStmt.setLong(1, currentLoggedInAccount);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    double currentBalance = rs.getDouble("balance");

                    if (currentBalance >= amount) {
                        try (
                            PreparedStatement updateStmt = conn.prepareStatement(updateBalanceSQL);
                            PreparedStatement insertStmt = conn.prepareStatement(insertTransactionSQL, Statement.RETURN_GENERATED_KEYS)
                        ) {
                            // Update balance
                            updateStmt.setDouble(1, amount);
                            updateStmt.setLong(2, currentLoggedInAccount);
                            updateStmt.executeUpdate();

                            // Insert transaction
                            insertStmt.setLong(1, currentLoggedInAccount);
                            insertStmt.setString(2, "Withdrawal");
                            insertStmt.setDouble(3, amount);
                            insertStmt.executeUpdate();

                            // Get transaction ID
                            ResultSet generatedKeys = insertStmt.getGeneratedKeys();
                            if (generatedKeys.next()) {
                                int transactionID = generatedKeys.getInt(1);

                                conn.commit();

                                JOptionPane.showMessageDialog(null,
                                    "Withdrawal successful!\nTransaction ID: " + transactionID +
                                    "\nAmount: " + amount +
                                    "\nRemaining Balance: " + (currentBalance - amount),
                                    "Success", JOptionPane.INFORMATION_MESSAGE);
                                return true;
                            } else {
                                conn.rollback();
                                JOptionPane.showMessageDialog(null,
                                    "Transaction failed: No transaction ID returned.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                                return false;
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null,
                            "Insufficient balance! Current balance: " + currentBalance,
                            "Error", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                        "Account not found.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }

        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(null,
                "Database error: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean changePin(long accountNumber, String oldPin, String newPin, String confirmNewPin) {
        if (accountNumber <= 0) {
            JOptionPane.showMessageDialog(null,
                "Invalid account number.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!newPin.equals(confirmNewPin)) {
            JOptionPane.showMessageDialog(null,
                "New PIN and confirmation PIN do not match.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String checkPinSQL = "SELECT pin FROM accounts WHERE account_number = ?";
        String updatePinSQL = "UPDATE accounts SET pin = ? WHERE account_number = ?";

        try (Connection conn = getConnection();
        PreparedStatement checkStmt = conn.prepareStatement(checkPinSQL)) {

            // Verify old PIN
            checkStmt.setLong(1, accountNumber);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                String currentPin = rs.getString("pin");

                if (!currentPin.equals(oldPin)) {
                    JOptionPane.showMessageDialog(null,
                        "Old PIN is incorrect.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                // Update PIN
                try (PreparedStatement updateStmt = conn.prepareStatement(updatePinSQL)) {
                    updateStmt.setString(1, newPin);
                    updateStmt.setLong(2, accountNumber);
                    int rowsUpdated = updateStmt.executeUpdate();

                    if (rowsUpdated > 0) {
                        JOptionPane.showMessageDialog(null,
                            "PIN changed successfully!",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                        return true;
                    } else {
                        JOptionPane.showMessageDialog(null,
                            "Failed to change PIN.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null,
                    "Account not found.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                "Database error: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

}
