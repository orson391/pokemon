import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
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

    public int searchUser(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username); // Set the username parameter value
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String foundUsername = rs.getString("username");
                    System.out.println("Username found: " + foundUsername);
                    return 1; // User found
                } else {
                    System.out.println("No user found with the username: " + username);
                    return 0; // No user found
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Error occurred
    }
    


    int sendRequest(String u, String r) {
        String senderQuery = "SELECT user_id FROM users WHERE username = ?";
        String receiverQuery = "SELECT user_id FROM users WHERE username = ?";
        String insertQuery = "INSERT INTO friend_requests (sender_id, recipient_id) VALUES (?, ?)";
        
        try (PreparedStatement senderStmt = conn.prepareStatement(senderQuery);
             PreparedStatement receiverStmt = conn.prepareStatement(receiverQuery)) {
            
            // Set parameters for sender query
            senderStmt.setString(1, u);
            try (ResultSet senderRs = senderStmt.executeQuery()) {
                if (senderRs.next()) {
                    int senderId = senderRs.getInt("user_id");
                    
                    // Set parameters for receiver query
                    receiverStmt.setString(1, r);
                    try (ResultSet receiverRs = receiverStmt.executeQuery()) {
                        if (receiverRs.next()) {
                            int receiverId = receiverRs.getInt("user_id");
                            
                            // Execute insert query
                            try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                                insertStmt.setInt(1, senderId);
                                insertStmt.setInt(2, receiverId);
                                insertStmt.executeUpdate();
                                System.out.println("Request sent successfully");
                                return 1; // Request sent successfully
                            }
                        } else {
                            System.out.println("Receiver not found");
                            return 0; // Receiver not found
                        }
                    }
                } else {
                    System.out.println("Sender not found");
                    return 0; // Sender not found
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Error occurred
        }
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
