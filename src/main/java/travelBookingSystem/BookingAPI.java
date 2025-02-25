package travelBookingSystem;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.sql.*;
import java.util.*;

@Path("/bookings")
public class BookingAPI {
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String createBooking(Booking booking) {
        if (isBookingExists(booking.getBookingId())) {
            return "{\"error\": \"Booking already exists\"}";
        }

        String insertQuery = "INSERT INTO bookings (booking_id, user_id, package_id, travel_date, number_of_travelers) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, booking.getBookingId());
            preparedStatement.setString(2, booking.getUserId());
            preparedStatement.setInt(3, booking.getPackageId());
            preparedStatement.setString(4, booking.getTravelDate());
            preparedStatement.setInt(5, booking.getNumberOfTravelers());

            preparedStatement.executeUpdate();
            return "{\"status\": \"Booking created successfully\"}";
        } catch (SQLException e) {
            e.printStackTrace();
            return "{\"error\": \"Failed to create booking\"}";
        }
    }

    @GET
    @Path("/{bookingId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getBooking(@PathParam("bookingId") String bookingId) {
        Booking booking = getBookingFromDatabase(bookingId);
        if (booking != null) {
            return booking.toJson();
        } else {
            return "{\"error\": \"Booking not found\"}";
        }
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllBookings() {
        List<Booking> bookingsList = getAllBookingsFromDatabase();
        return "{\"bookings\": " + bookingsListToJson(bookingsList) + "}";
    }

    @PUT
    @Path("/{bookingId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateBooking(@PathParam("bookingId") String bookingId, Booking updatedBooking) {
        if (!isBookingExists(bookingId)) {
            return "{\"error\": \"Booking not found\"}";
        }

        String updateQuery = "UPDATE bookings SET user_id = ?, package_id = ?, travel_date = ?, number_of_travelers = ? WHERE booking_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, updatedBooking.getUserId());
            preparedStatement.setInt(2, updatedBooking.getPackageId());
            preparedStatement.setString(3, updatedBooking.getTravelDate());
            preparedStatement.setInt(4, updatedBooking.getNumberOfTravelers());
            preparedStatement.setString(5, bookingId);

            preparedStatement.executeUpdate();
            return "{\"status\": \"Booking updated successfully\"}";
        } catch (SQLException e) {
            e.printStackTrace();
            return "{\"error\": \"Failed to update booking\"}";
        }
    }

    @DELETE
    @Path("/{bookingId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteBooking(@PathParam("bookingId") String bookingId) {
        if (!isBookingExists(bookingId)) {
            return "{\"error\": \"Booking not found\"}";
        }

        String deleteQuery = "DELETE FROM bookings WHERE booking_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            preparedStatement.setString(1, bookingId);
            preparedStatement.executeUpdate();
            return "{\"status\": \"Booking deleted successfully\"}";
        } catch (SQLException e) {
            e.printStackTrace();
            return "{\"error\": \"Failed to delete booking\"}";
        }
    }

    private boolean isBookingExists(String bookingId) {
        String checkQuery = "SELECT booking_id FROM bookings WHERE booking_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(checkQuery)) {

            preparedStatement.setString(1, bookingId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Booking getBookingFromDatabase(String bookingId) {
        String query = "SELECT * FROM bookings WHERE booking_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, bookingId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Booking(
                        resultSet.getString("booking_id"),
                        resultSet.getString("user_id"),
                        resultSet.getInt("package_id"),
                        resultSet.getDate("travel_date").toString(),
                        resultSet.getInt("number_of_travelers")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Booking> getAllBookingsFromDatabase() {
        List<Booking> bookingsList = new ArrayList<>();
        String query = "SELECT * FROM bookings";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                bookingsList.add(new Booking(
                        resultSet.getString("booking_id"),
                        resultSet.getString("user_id"),
                        resultSet.getInt("package_id"),
                        resultSet.getDate("travel_date").toString(),
                        resultSet.getInt("number_of_travelers")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookingsList;
    }

    private String bookingsListToJson(List<Booking> bookingsList) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[");
        for (Booking booking : bookingsList) {
            jsonBuilder.append(booking.toJson()).append(",");
        }
        if (!bookingsList.isEmpty()) {
            jsonBuilder.setLength(jsonBuilder.length() - 1); 
        }
        jsonBuilder.append("]");
        return jsonBuilder.toString();
    }
}
