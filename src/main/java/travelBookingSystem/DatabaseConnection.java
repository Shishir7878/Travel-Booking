package travelBookingSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String CONNECTION_URL = "jdbc:sqlserver://localhost:1433;databaseName=travel_booking_system;integratedSecurity=true;encrypt=false;trustServerCertificate=true;";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CONNECTION_URL);
  }      
}
