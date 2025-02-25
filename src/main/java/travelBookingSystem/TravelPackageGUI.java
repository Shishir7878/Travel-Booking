package travelBookingSystem;

import javax.swing.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;

public class TravelPackageGUI {
    public JFrame frame;
    private JTable packageTable;
    private JTextField searchField;
    private JButton searchButton;
    private JButton backButton;
    private JButton bookButton;

    private List<TravelPackage> travelPackages;  
    public TravelPackageGUI() {
        createFrame();
    }
        
    public void createFrame() {
        // Create the frame
        frame = new JFrame("Travel Booking System");
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());
        JPanel searchPanel = new JPanel();
        frame.getContentPane().add(searchPanel, BorderLayout.NORTH);
        searchPanel.setLayout(new FlowLayout());

        JLabel searchLabel = new JLabel("Search by Destination:");
        searchPanel.add(searchLabel);

        searchField = new JTextField();
        searchPanel.add(searchField);
        searchField.setColumns(20);

        searchButton = new JButton("Search");
        searchPanel.add(searchButton);

        backButton = new JButton("Back");
        searchPanel.add(backButton);

        packageTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(packageTable);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        bookButton = new JButton("Book");
        searchPanel.add(bookButton); 
        bookButton.setEnabled(false); 

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String destination = searchField.getText();
                if (!destination.isEmpty()) {
                    searchTravelPackages(destination);
                } else {
                    fetchAllTravelPackages();
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fetchAllTravelPackages();
            }
        });

        bookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = packageTable.getSelectedRow();
                if (selectedRow != -1) {
                    int packageId = (int) packageTable.getValueAt(selectedRow, 0); 
                    TravelPackage selectedPackage = getPackageById(packageId);
                    if (selectedPackage != null) {
                        BookingGUI bookingGUI = new BookingGUI(selectedPackage);
                        bookingGUI.setVisible(true);
                        frame.setVisible(false); 
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a package to book.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        packageTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && packageTable.getSelectedRow() != -1) {
                bookButton.setEnabled(true); 
            }
        });

        fetchAllTravelPackages();
    }

    private void fetchAllTravelPackages() {

        String url = "http://localhost:8080/TravelBookingSystemProject/rest/travelPackages";

        try {
            String response = makeHttpRequest(url, "GET", null);
            ObjectMapper mapper = new ObjectMapper();
            travelPackages = mapper.readValue(response, new TypeReference<List<TravelPackage>>() {});

            updateTable(travelPackages);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error fetching travel packages.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchTravelPackages(String destination) {
        String url = "http://localhost:8080/TravelBookingSystemProject/rest/travelPackages/search?destination=" + destination;

        try {
            String response = makeHttpRequest(url, "GET", null);
            ObjectMapper mapper = new ObjectMapper();
            List<TravelPackage> packages = mapper.readValue(response, new TypeReference<List<TravelPackage>>() {});

            updateTable(packages);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error searching for travel packages.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String makeHttpRequest(String urlString, String method, String jsonBody) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(new URI(urlString))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json");

        if ("POST".equals(method) && jsonBody != null) {
            requestBuilder.method(method, BodyPublishers.ofString(jsonBody));
        } else {
            requestBuilder.method(method, HttpRequest.BodyPublishers.noBody());
        }

        HttpRequest request = requestBuilder.build();


        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());


        int status = response.statusCode();
        if (status >= 300) {

            throw new Exception("Request failed with status code: " + status);
        }


        return response.body();
    }

    private void updateTable(List<TravelPackage> packages) {

        String[] columnNames = {"ID", "Destination", "Price"};
        Object[][] data = new Object[packages.size()][3];

        for (int i = 0; i < packages.size(); i++) {
            TravelPackage packageItem = packages.get(i);
            data[i][0] = packageItem.getPackageId();
            data[i][1] = packageItem.getDestination();
            data[i][2] = packageItem.getPrice();
        }

        packageTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }

    private TravelPackage getPackageById(int packageId) {
        for (TravelPackage travelPackage : travelPackages) {
            if (travelPackage.getPackageId() == packageId) {
                return travelPackage;
            }
        }
        return null;
    }
}
