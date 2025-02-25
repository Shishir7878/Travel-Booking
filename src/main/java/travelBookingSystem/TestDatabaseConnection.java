package travelBookingSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDatabaseConnection {
    private static final String CONNECTION_URL = "jdbc:sqlserver://localhost:1433;databaseName=travel_booking_system;integratedSecurity=true;encrypt=false;trustServerCertificate=true;";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Load the SQL Server JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            
            // Establish the connection
            conn = DriverManager.getConnection(CONNECTION_URL);
            System.out.println("Database connection successful!");
        } catch (ClassNotFoundException e) {
            System.err.println("SQL Server JDBC Driver not found. Make sure it's added to your classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }

    public static void main(String[] args) {
        // Test the connection
        Connection connection = getConnection();
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection closed successfully.");
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
