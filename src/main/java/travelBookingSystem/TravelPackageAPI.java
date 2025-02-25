package travelBookingSystem;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("/travelPackages")
public class TravelPackageAPI {

    // View all travel packages
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TravelPackage> getPackages() {
        List<TravelPackage> travelPackages = new ArrayList<>();
        String query = "SELECT * FROM travel_packages";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                travelPackages.add(new TravelPackage(
                        resultSet.getInt("package_id"),
                        resultSet.getString("destination"),
                        resultSet.getDouble("price"),
                        resultSet.getString("description")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return travelPackages;
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchPackagesByDestination(@QueryParam("destination") String destination) {
        List<TravelPackage> travelPackages = new ArrayList<>();
        String query = "SELECT * FROM travel_packages WHERE destination LIKE ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, "%" + destination + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                travelPackages.add(new TravelPackage(
                        resultSet.getInt("package_id"),
                        resultSet.getString("destination"),
                        resultSet.getDouble("price"),
                        resultSet.getString("description")
                ));
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error searching travel packages").build();
        }

        if (travelPackages.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();  
        }

        return Response.ok(travelPackages).build();
    }
}
