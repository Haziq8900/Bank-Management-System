package Model;

import Backend.Account;

import java.sql.Connection;
import java.sql.SQLException;

public class AccountDatabase {
    private Connection connection;

    public AccountDatabase() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }


    public void createAccount(Account account) throws SQLException {
        String query = "";
        if(account.getPin() == null || account.getPin().isEmpty()){
          query = "insert into accounts (account_number, name, cnic, phone, account_type, balance) values (?, ?, ?, ?, ?, ?)";
        }else
            query = "insert into accounts (account_number, name, cnic, phone, account_type, balance, pin) values (?, ?, ?, ?, ?, ?, ?)";
        try (java.sql.PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, account.getAccount_number());
            statement.setString(2, account.getName());
            statement.setString(3, account.getCnic());
            statement.setString(4, account.getPhone());
            statement.setInt(5, account.getAccount_type());
            statement.setDouble(6, account.getBalance());
            if (account.getPin() != null && !account.getPin().isEmpty()){
                statement.setString(7, account.getPin());
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error creating account: " + e.getMessage());
        }
    }

    public void depositFund(Account account) throws SQLException{
        String query = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
        try (java.sql.PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, account.getBalance());
            statement.setString(2, account.getAccount_number());
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Account not found with account_number: " + account.getAccount_number());
            }
        } catch (SQLException e) {
            throw new SQLException("Error depositing funds: " + e.getMessage());
        }
    }

    public double getBalance(Account account) throws SQLException{
        String query = "SELECT balance FROM accounts WHERE account_number = ?";
        try (java.sql.PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, account.getAccount_number());
            try (java.sql.ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble(1);
                }
                return 0.0;
            }
        } catch (SQLException e) {
            throw new SQLException("Error getting balance: " + e.getMessage());
        }
    }

    public boolean deductBalance(Account account, double amount) throws SQLException{
        String query = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
        try (java.sql.PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, amount);
            statement.setString(2, account.getAccount_number());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected != 0;
        }
    }

}
