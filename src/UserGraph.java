import java.util.*;

public class UserGraph {
    private Map<String, Set<String>> friendships; // Map to store established friendships
    private Map<String, Set<String>> friendRequests; // Map to store pending friend requests

    public UserGraph() {
        friendships = new HashMap<>();
        friendRequests = new HashMap<>();
    }

    // Method to add a user to the graph
    public void addUser(String user) {
        if (!friendships.containsKey(user)) {
            friendships.put(user, new HashSet<>());
            friendRequests.put(user, new HashSet<>());
        }
    }

    // Method to send a friend request from user1 to user2
    public void sendFriendRequest(String user1, String user2) {
        addUser(user1);
        addUser(user2);
        friendRequests.get(user2).add(user1);
    }

    // Method to accept a friend request
    public void acceptFriendRequest(String user1, String user2) {
        addUser(user1);
        addUser(user2);
        // Add user1 to user2's friends
        friendships.get(user2).add(user1);
        // Add user2 to user1's friends
        friendships.get(user1).add(user2);
        // Remove the friend request
        friendRequests.get(user1).remove(user2);
    }

    // Method to reject a friend request
    public void rejectFriendRequest(String user1, String user2) {
        addUser(user1);
        addUser(user2);
        // Remove the friend request
        friendRequests.get(user1).remove(user2);
    }

    // Method to get friends of a user
    public Set<String> getFriends(String user) {
        return friendships.getOrDefault(user, Collections.emptySet());
    }

    // Method to get pending friend requests for a user
    public Set<String> getPendingFriendRequests(String user) {
        return friendRequests.getOrDefault(user, Collections.emptySet());
    }

    public void gg()
    {
        UserGraph userGraph = new UserGraph();

        // Adding users
        userGraph.addUser("Alice");
        userGraph.addUser("Bob");
        userGraph.addUser("Charlie");

        // Sending friend requests
        userGraph.sendFriendRequest("Alice", "Bob");
        userGraph.sendFriendRequest("Bob", "Charlie");

        // Accepting friend requests
        userGraph.acceptFriendRequest("Alice", "Bob");
        userGraph.acceptFriendRequest("Bob", "Charlie");

        // Getting friends of a user
        System.out.println("Friends of Alice: " + userGraph.getFriends("Alice"));
        System.out.println("Friends of Bob: " + userGraph.getFriends("Bob"));
        System.out.println("Friends of Charlie: " + userGraph.getFriends("Charlie"));
    }
}
