package travelBookingSystem;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.sql.*;

@Path("/users")
public class UserAPI {

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String registerUser(User user) {
        validateUserInput(user);
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT email FROM users WHERE email = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, user.getEmail());
                ResultSet resultSet = stmt.executeQuery();
                if (resultSet.next()) {
                    throw new WebApplicationException("User already exists", 400);
                }
            }

            query = "INSERT INTO users (email, password) VALUES (?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, user.getEmail());
                stmt.setString(2, user.getPassword());
                stmt.executeUpdate();
            }
            return "{\"status\": \"User registered successfully\"}";
        } catch (SQLException e) {
            throw new WebApplicationException("Database error", 500);
        }
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String loginUser(User user) {
        validateUserInput(user);
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT password FROM users WHERE email = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, user.getEmail());
                ResultSet resultSet = stmt.executeQuery();
                if (resultSet.next() && resultSet.getString("password").equals(user.getPassword())) {
                    return "{\"status\": \"Login successful\"}";
                }
            }
            throw new WebApplicationException("Invalid credentials", 401);
        } catch (SQLException e) {
            throw new WebApplicationException("Database error", 500);
        }
    }

    @GET
    @Path("/check/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public String checkUserExists(@PathParam("email") String email) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT email FROM users WHERE email = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, email);
                ResultSet resultSet = stmt.executeQuery();
                if (resultSet.next()) {
                    return "{\"status\": \"User is registered\"}";
                }
            }
            throw new WebApplicationException("User not found", 404);
        } catch (SQLException e) {
            throw new WebApplicationException("Database error", 500);
        }
    }

    private void validateUserInput(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty() || 
            user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new WebApplicationException("Invalid user data", 400);
        }
    }
}
