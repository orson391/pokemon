import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PokemonGameMenu extends JFrame {

    private String username;
    private String password;
    private db con;

    public PokemonGameMenu() {
        setTitle("Pokemon Game Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null); // Center the window
        int lg = -1;
        do{
            JTextField usernameField = new JTextField();
            JPasswordField passwordField = new JPasswordField();
            Object[] message = {
                "Username:", usernameField,
                "Password:", passwordField
            };
            int option = JOptionPane.showConfirmDialog(this, message, "Login", JOptionPane.OK_CANCEL_OPTION);
            if (option != JOptionPane.OK_OPTION) {
                JOptionPane.showMessageDialog(this, "Login canceled.", "Warning", JOptionPane.WARNING_MESSAGE);
                
            }
            username = usernameField.getText().trim();
            char[] passwordChars = passwordField.getPassword();
            password = new String(passwordChars).trim();
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter both username and password.", "Warning", JOptionPane.WARNING_MESSAGE);
                
            }
            con = new db(); // Initialize the database connection object
            lg = con.insertUser(username, password); // Insert user into the database
            if (lg == -1) {
                JOptionPane.showMessageDialog(this, "Invalid Username Or Password", "Warning", JOptionPane.WARNING_MESSAGE);
            }
            
        }while(lg<0);
        
        Client l1 = new Client(username);
        l1.sendNameToServer();

        JButton startGameButton = new JButton("Start Game");
        JButton selectPokemonButton = new JButton("Select Pokemon");
        JButton selectFireendButton = new JButton("Fireends");
        JButton selectProfileButton = new JButton("Profile");
        JButton quitButton = new JButton("Quit");
    
        selectProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameprofile();
            }
        });

        selectFireendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamefireend();
            }
        });
    
        // Add action listeners
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
    
        selectPokemonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectPokemon();
            }
        });
    
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    
        // Create layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1)); // Changed to accommodate the Profile button
        panel.add(startGameButton);
        panel.add(selectPokemonButton);
        panel.add(selectFireendButton);
        panel.add(selectProfileButton); // Added the Profile button
        panel.add(quitButton);
    
        // Add panel to frame
        add(panel);
    }

    private void gameprofile() {
        // Add logic to start the game
        System.out.println("Game Profile: " + username);
    }
    private void gamefireend() {
        // Add logic to start the game
        FriendsGUI fireend = new FriendsGUI(username,con);
        
        
        System.out.println("Game Fireends: " + username);
    }

    private void startGame() {
        // Add logic to start the game
        System.out.println("Starting new game for username: " + username);
    }

    private void selectPokemon() {
        // Add logic to select Pokemon
        System.out.println("Selecting Pokemon for username: " + username);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PokemonGameMenu menu = new PokemonGameMenu();
                menu.setVisible(true);
            }
        });
    }
}
