package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class to retrieve account statistics from the database
 */
public class AccountStatistics {
    private Connection connection;

    public AccountStatistics() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }

    /**
     * Get the total number of accounts in the bank
     * @return total number of accounts
     */
    public int getTotalAccounts() throws SQLException {
        String query = "SELECT COUNT(*) FROM accounts";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new SQLException("Error getting total accounts: " + e.getMessage());
        }
    }

    /**
     * Get the number of savings accounts in the bank
     * @return number of savings accounts
     */
    public int getSavingsAccounts() throws SQLException {
        String query = "SELECT COUNT(*) FROM accounts WHERE account_type = 'Savings'";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new SQLException("Error getting savings accounts: " + e.getMessage());
        }
    }

    /**
     * Get the number of current accounts in the bank
     * @return number of current accounts
     */
    public int getCurrentAccounts() throws SQLException {
        String query = "SELECT COUNT(*) FROM accounts WHERE account_type = 'Current'";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new SQLException("Error getting current accounts: " + e.getMessage());
        }
    }

    /**
     * Get the total amount in the bank
     * @return total amount in the bank
     */
    public double getTotalAmount() throws SQLException {
        String query = "SELECT SUM(balance) FROM accounts";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getDouble(1);
            }
            return 0.0;
        } catch (SQLException e) {
            throw new SQLException("Error getting total amount: " + e.getMessage());
        }
    }
}
