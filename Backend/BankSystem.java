package Backend;

import java.sql.*;
import java.util.Random;
import Model.Account;

public class BankSystem {

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/ATM_DB", "root", "HaziqKhan@30-01-2005");
    }

    public boolean openAccount(Account account) {
        String insertSQL = "INSERT INTO accounts (customer_id, account_number, account_type, balance) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
            PreparedStatement insertStmt = conn.prepareStatement(insertSQL)) {

            long accountNumber = generateUniqueAccountNumber(conn);  // Generate 16-digit unique number

            insertStmt.setInt(1, account.getCustomer_id());
            insertStmt.setLong(2, accountNumber);
            insertStmt.setString(3, account.getAccount_type());
            insertStmt.setDouble(4, account.getBalance());

            int rowsInserted = insertStmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Account created successfully. Account Number: " + accountNumber);
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
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

    public boolean depositFunds(int account_id, double amount) {
        String updateSQL = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";
    }
}
