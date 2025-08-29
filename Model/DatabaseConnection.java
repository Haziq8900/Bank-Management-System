package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String databaseName = "ATM_DB"; // change it accordingly
    private static final String URL = "jdbc:mysql://localhost:3306/" + databaseName;
    private static final String USER = "root";  // change it accordingly
    private static final String PASSWORD = "HaziqKhan@30-01-2005";  // change it accordingly

    static {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load MySQL JDBC driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        // Return a new database connection
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                System.out.println("Database connection successful!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
    }
}