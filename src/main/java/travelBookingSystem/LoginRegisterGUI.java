package travelBookingSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;

public class LoginRegisterGUI {

    private boolean isLoggedIn = false;
    private JFrame frame;

    public void createFrame() {
        frame = new JFrame("Travel Booking System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 350);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Travel Booking System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20)); 
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JTabbedPane tabbedPane = new JTabbedPane();
        panel.add(tabbedPane);

        JPanel loginPanel = createLoginPanel();
        tabbedPane.addTab("Login", loginPanel);


        JPanel registerPanel = createRegisterPanel();
        tabbedPane.addTab("Register", registerPanel);

        frame.setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); 
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); 
 
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(0, 123, 255));  
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setPreferredSize(new Dimension(100, 40));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(loginButton);

        JLabel statusLabel = new JLabel("");
        statusLabel.setForeground(Color.RED);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(statusLabel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                new SwingWorker<String, Void>() {
                    @Override
                    protected String doInBackground() throws Exception {
                        return sendPostRequest("http://localhost:8080/TravelBookingSystemProject/rest/users/login", email, password);
                    }

                    @Override
                    protected void done() {
                        try {
                            String response = get();
                            if ("Success".equals(response)) {
                                isLoggedIn = true;
                                frame.dispose();  
                                openTravelPackageGUI();
                            } else {
                                statusLabel.setText(response); 
                            }
                        } catch (Exception ex) {
                            statusLabel.setText("Error: Unable to connect to the server");
                        }
                    }
                }.execute(); 
            }
        });

        return panel;
    }


    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Password Input
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        JButton registerButton = createButton("Register", new Color(40, 167, 69));
        panel.add(registerButton);

        JLabel statusLabel = new JLabel("");
        statusLabel.setForeground(Color.RED);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(statusLabel);

        registerButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            if (email.isEmpty() || password.isEmpty()) {
                statusLabel.setText("Please enter both email and password.");
            } else {
                new Thread(() -> {
                    String response = sendPostRequest("http://localhost:8080/TravelBookingSystemProject/rest/users/register", email, password);
                    SwingUtilities.invokeLater(() -> statusLabel.setText(response));
                }).start();
            }
        });

        return panel;
    }

    private String sendPostRequest(String apiUrl, String email, String password) {
        try {
            JSONObject jsonPayload = new JSONObject();
            jsonPayload.put("email", email);
            jsonPayload.put("password", password);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload.toString(), StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200 || response.statusCode() == 201) {
                return "Success";
            } else {
                return "Error: " + response.statusCode() + " " + response.body();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Unable to connect to the server";
        }
    }

    private void openTravelPackageGUI() {
        TravelPackageGUI travelPackageGUI = new TravelPackageGUI();
        travelPackageGUI.createFrame();
        travelPackageGUI.frame.setVisible(true);
    }
    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(100, 40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

}
