import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class FriendsGUI extends JFrame {
    private List<User> users;
    String use;

    public FriendsGUI(String u, db con) {
        setTitle("Friends GUI");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        use = u;
        users = new ArrayList<>(); // Initialize the list of users

        JPanel mainPanel = new JPanel(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        // Enhancing the Search Users panel
        JPanel searchUsersPanel = new JPanel();
        searchUsersPanel.setLayout(new BoxLayout(searchUsersPanel, BoxLayout.Y_AXIS));
        searchUsersPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding

        JTextField searchField = new JTextField(20);
        searchField.setMaximumSize(new Dimension(Integer.MAX_VALUE, searchField.getPreferredSize().height)); // Allow expanding horizontally
        JButton searchButton = new JButton("Search");
        searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = searchField.getText();
                int b = con.searchUser(query);

                if (b == 1) {
                    searchUsersPanel.removeAll();

                    JLabel nameLabel = new JLabel("Friend found: " + query);
                    nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    searchUsersPanel.add(nameLabel);

                    JButton addButton = new JButton("Challenge");
                    addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                    addButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Implement logic to add the user as a friend
                        }
                    });
                    searchUsersPanel.add(addButton);

                    JButton requestButton = new JButton("Send Friend Request");
                    requestButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                    requestButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Implement logic to send a friend request to the user
                        }
                    });
                    searchUsersPanel.add(requestButton);

                    revalidate();
                    repaint();
                } else {
                    JLabel notfoundLabel = new JLabel(query + ": not found");
                    notfoundLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    searchUsersPanel.add(notfoundLabel);

                    revalidate();
                    repaint();
                }
            }
        });
        searchUsersPanel.add(searchField);
        searchUsersPanel.add(Box.createVerticalStrut(10)); // Add spacing
        searchUsersPanel.add(searchButton);

        tabbedPane.addTab("Search Users", searchUsersPanel);

        JPanel friendRequestsPanel = new JPanel();
        // Implement friend requests UI components
        tabbedPane.addTab("Friend Requests", friendRequestsPanel);

        JPanel activeFriendsPanel = new JPanel(new GridLayout(0, 1));
        // Implement active friends UI components
        for (User user : users) {
            JButton friendButton = new JButton(user.getName());
            friendButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openChatWindow(user);
                    openChallengeWindow(user);
                }
            });
            activeFriendsPanel.add(friendButton);
        }

        tabbedPane.addTab("Active Friends", activeFriendsPanel);

        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        add(mainPanel);

        setVisible(true);
    }

    private void openChatWindow(User friend) {
        JFrame chatWindow = new JFrame("Chat with " + friend.getName());
        chatWindow.setSize(400, 300);
        chatWindow.setVisible(true);
    }

    private void openChallengeWindow(User friend) {
        JFrame challengeWindow = new JFrame("Challenge " + friend.getName());
        challengeWindow.setSize(400, 300);
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
}
