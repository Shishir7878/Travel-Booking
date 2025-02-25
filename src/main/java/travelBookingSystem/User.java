package travelBookingSystem;

import java.io.BufferedReader;
import java.io.IOException;

public class User {
    private String email;
    private String password;

    public User() {}

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toText() {
        return email + "\n" + password;
    }

    public static User fromText(BufferedReader reader) throws IOException  {
        String email = reader.readLine();
        String password = reader.readLine();
        reader.readLine(); 
        return (email != null && password != null) ? new User(email, password) : null;
    }
}
