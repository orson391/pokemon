import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class db {
    private Connection conn;

    db() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Connect to MySQL database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pokemon", "root", "");
            if (conn != null) {
                System.out.println("Connected to database successfully!");
            } else {
                System.out.println("Failed to connect to database.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Connection failed! Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    int insertUser(String username, String password) {
        String hashedPassword = hashPassword(password);
        if (hashedPassword == null) {
            System.out.println("Failed to hash the password.");
            return -3;
        }
    
        try (PreparedStatement checkStatement = conn.prepareStatement("SELECT * FROM users WHERE username=?");
            PreparedStatement insertStatement = conn.prepareStatement("INSERT INTO users (username, password_hash) VALUES (?, ?)")) {
    
            // Check if the username already exists
            checkStatement.setString(1, username);
            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next()) {
                // Username exists, check password
                String storedPasswordHash = resultSet.getString("password_hash");
                if (storedPasswordHash.equals(hashedPassword)) {
                    // Passwords match, welcome back
                    System.out.println("Welcome back, " + username);
                    return 0;
                } else {
                    // Incorrect password
                    System.out.println("Invalid password for user: " + username);
                    return -1; // Exit method, no need to insert the user
                }
                
            }
    
            // Insert user data into the database
            insertStatement.setString(1, username);
            insertStatement.setString(2, hashedPassword);
            int rowsAffected = insertStatement.executeUpdate();
            System.out.println(rowsAffected + " rows inserted.");
            System.out.println("Welcome new user, " + username);
        } catch (SQLException e) {
            System.out.println("Error inserting or checking user: " + e.getMessage());
            return -3;
        }
        return 1;
        
    }
    

    static String hashPassword(String password) {
        try {
            // Create MessageDigest instance for SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Add password bytes to digest
            md.update(password.getBytes());

            // Get the hash's bytes
            byte[] bytes = md.digest();

            // Convert bytes to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }

            // Get complete hashed password in hexadecimal format
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
