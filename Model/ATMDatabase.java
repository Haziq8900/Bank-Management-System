package Model;

import Backend.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ATMDatabase {
    private Connection connection;

    public ATMDatabase() throws SQLException{
        connection = DatabaseConnection.getConnection();
    }

    public void loginATM(Account account) throws SQLException {
        String query = "SELECT account_number, pin from accounts where account_number = ? and pin = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, account.getAccount_number());
            statement.setString(2, account.getPin());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    throw new SQLException("Invalid Credentials! " + account.getAccount_number());
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error logging in: " + e.getMessage());
        }
    }

    public boolean changePin(String accountNumber, String oldPin, String newPin) throws SQLException {
        String query = "UPDATE accounts SET pin = ? WHERE account_number = ? AND pin = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newPin);
            statement.setString(2, accountNumber);
            statement.setString(3, oldPin);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0; // true if updated, false if wrong oldPin/account
        } catch (SQLException e) {
            throw new SQLException("Error changing PIN: " + e.getMessage());
        }
    }

}
