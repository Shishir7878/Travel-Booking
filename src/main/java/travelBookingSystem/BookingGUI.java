package travelBookingSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URI;
import java.net.http.*;
import java.net.http.HttpRequest.*;
import org.json.JSONObject;

public class BookingGUI extends JFrame {

    private JTextField bookingIdField, userIdField, packageIdField, travelDateField, travelersField;
    private JTextArea outputArea;
    private JButton createButton, readButton, updateButton, deleteButton, getAllButton;
    private TravelPackage travelPackage; 

    private static final String BASE_URL = "http://localhost:8080/TravelBookingSystemProject/rest/bookings";

    public BookingGUI(TravelPackage travelPackage) {
        this.travelPackage = travelPackage; 

        setTitle("Travel Booking System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 2));

        inputPanel.add(new JLabel("Booking ID:"));
        bookingIdField = new JTextField();
        inputPanel.add(bookingIdField);

        inputPanel.add(new JLabel("User ID:"));
        userIdField = new JTextField();
        inputPanel.add(userIdField);

        inputPanel.add(new JLabel("Package ID:"));
        packageIdField = new JTextField();
        packageIdField.setText(String.valueOf(travelPackage.getPackageId()));
        packageIdField.setEditable(false); 
        inputPanel.add(packageIdField);

        inputPanel.add(new JLabel("Travel Date:"));
        travelDateField = new JTextField();
        inputPanel.add(travelDateField);

        inputPanel.add(new JLabel("Number of Travelers:"));
        travelersField = new JTextField();
        inputPanel.add(travelersField);

        JPanel buttonPanel = new JPanel();
        createButton = new JButton("Create Booking");
        readButton = new JButton("Get Booking");
        updateButton = new JButton("Update Booking");
        deleteButton = new JButton("Delete Booking");
        getAllButton = new JButton("Get All Bookings");

        buttonPanel.add(createButton);
        buttonPanel.add(readButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(getAllButton);

        outputArea = new JTextArea(5, 30);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        createButton.addActionListener(e -> createBooking());
        readButton.addActionListener(e -> getBooking());
        updateButton.addActionListener(e -> updateBooking());
        deleteButton.addActionListener(e -> deleteBooking());
        getAllButton.addActionListener(e -> getAllBookings());
    }

    private void createBooking() {
        try {
            String bookingId = bookingIdField.getText();
            String userId = userIdField.getText();
            int packageId = Integer.parseInt(packageIdField.getText()); 
            String travelDate = travelDateField.getText();
            int numberOfTravelers = Integer.parseInt(travelersField.getText());

            JSONObject bookingData = new JSONObject();
            bookingData.put("bookingId", bookingId);
            bookingData.put("userId", userId);
            bookingData.put("packageId", packageId);
            bookingData.put("travelDate", travelDate);
            bookingData.put("numberOfTravelers", numberOfTravelers);

            String response = sendRequest(BASE_URL + "/create", "POST", bookingData.toString());
            outputArea.setText(response);
        } catch (Exception ex) {
            ex.printStackTrace();
            outputArea.setText("Error creating booking");
        }
    }

    private void getBooking() {
        try {
            String bookingId = bookingIdField.getText();
            String response = sendRequest(BASE_URL + "/" + bookingId, "GET", null);
            outputArea.setText(response);
        } catch (Exception ex) {
            ex.printStackTrace();
            outputArea.setText("Error getting booking");
        }
    }

    private void updateBooking() {
        try {
            String bookingId = bookingIdField.getText();
            String userId = userIdField.getText();
            int packageId = Integer.parseInt(packageIdField.getText());
            String travelDate = travelDateField.getText();
            int numberOfTravelers = Integer.parseInt(travelersField.getText());

            JSONObject bookingData = new JSONObject();
            bookingData.put("userId", userId);
            bookingData.put("packageId", packageId);
            bookingData.put("travelDate", travelDate);
            bookingData.put("numberOfTravelers", numberOfTravelers);

            String response = sendRequest(BASE_URL + "/" + bookingId, "PUT", bookingData.toString());
            outputArea.setText(response);
        } catch (Exception ex) {
            ex.printStackTrace();
            outputArea.setText("Error updating booking");
        }
    }

    private void deleteBooking() {
        try {
            String bookingId = bookingIdField.getText();
            String response = sendRequest(BASE_URL + "/" + bookingId, "DELETE", null);
            outputArea.setText(response);
        } catch (Exception ex) {
            ex.printStackTrace();
            outputArea.setText("Error deleting booking");
        }
    }

    private void getAllBookings() {
        try {
            String response = sendRequest(BASE_URL + "/all", "GET", null);
            outputArea.setText(response);
        } catch (Exception ex) {
            ex.printStackTrace();
            outputArea.setText("Error fetching all bookings");
        }
    }

    private String sendRequest(String urlString, String method, String jsonInputString) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request;

        if (jsonInputString != null) {
            request = HttpRequest.newBuilder()
                    .uri(URI.create(urlString))
                    .method(method, HttpRequest.BodyPublishers.ofString(jsonInputString))
                    .header("Content-Type", "application/json")
                    .build();
        } else {
            request = HttpRequest.newBuilder()
                    .uri(URI.create(urlString))
                    .method(method, HttpRequest.BodyPublishers.noBody())
                    .build();
        }

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return "Response Code: " + response.statusCode() + "\n" + response.body();
    }
}
