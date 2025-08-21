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
        String query = "insert into accounts (account_number, name, cnic, phone, account_type, balance) values (?, ?, ?, ?, ?, ?)";
        try (java.sql.PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, account.getAccount_number());
            statement.setString(2, account.getName());
            statement.setString(3, account.getCnic());
            statement.setString(4, account.getPhone());
            statement.setInt(5, account.getAccount_type());
            statement.setDouble(6, account.getBalance());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error creating account: " + e.getMessage());
        }
    }

}
