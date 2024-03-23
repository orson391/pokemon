import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class FriendsGUI extends JFrame {
    private List<User> users;
    String use;
    
    public FriendsGUI(String u) {
        setTitle("Friends GUI");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        use = u;
        users = new ArrayList<>(); // Initialize the list of users

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel searchUsersPanel = new JPanel();
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = searchField.getText();
                List<User> searchResults = searchUsers(query);
                // Update UI to display search results
            }
        });
        searchUsersPanel.add(searchField);
        searchUsersPanel.add(searchButton);
        tabbedPane.addTab("Search Users", searchUsersPanel);

        JPanel friendRequestsPanel = new JPanel();
        // Implement friend requests UI components
        tabbedPane.addTab("Friend Requests", friendRequestsPanel);

        JPanel activeFriendsPanel = new JPanel(new GridLayout(0, 1)); // Layout for displaying buttons vertically
        // Implement active friends UI components
        for (User user : users) {
            JButton friendButton = new JButton(user.getName());
            friendButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Implement logic to chat with friend
                    openChatWindow(user);
                    // Implement logic to challenge friend
                    openChallengeWindow(user);
                }
            });
            activeFriendsPanel.add(friendButton);
        }

        tabbedPane.addTab("Active Friends", activeFriendsPanel);

        add(tabbedPane);

        setVisible(true);

        
    }

    private List<User> searchUsers(String query) {
        List<User> results = new ArrayList<>();
        for (User user : users) {
            if (user.getName().toLowerCase().contains(query.toLowerCase())) {
                results.add(user);
            }
        }
        return results;
    }

    private void openChatWindow(User friend) {
        // Implement chat window for the specified friend
        JFrame chatWindow = new JFrame("Chat with " + friend.getName());
        chatWindow.setSize(400, 300);
        // Add chat UI components
        chatWindow.setVisible(true);
    }

    private void openChallengeWindow(User friend) {
        // Implement challenge window for the specified friend
        JFrame challengeWindow = new JFrame("Challenge " + friend.getName());
        challengeWindow.setSize(400, 300);
        // Add challenge UI components
        challengeWindow.setVisible(true);
    }

    private class User {
        private String name;

        public User(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static void createGUI(String parameter) {
        SwingUtilities.invokeLater(() -> new FriendsGUI(parameter));
    }
}
