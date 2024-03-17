import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PokemonGameInterface extends JFrame {

    private JLabel playerLabel;
    private JLabel opponentLabel;
    private JButton[] moveButtons;

    public PokemonGameInterface() {
        setTitle("Pokemon Showdown");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Center the window

        // Initialize components
        playerLabel = new JLabel("Player: Pikachu (HP: 100)");
        opponentLabel = new JLabel("Opponent: Charmander (HP: 100)");

        // Initialize move buttons
        moveButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            moveButtons[i] = new JButton("Move " + (i + 1));
            moveButtons[i].addActionListener(new MoveButtonListener());
        }

        // Create layout
        JPanel mainPanel = new JPanel(new GridLayout(3, 1));
        mainPanel.add(playerLabel);
        mainPanel.add(opponentLabel);
        JPanel movesPanel = new JPanel(new GridLayout(1, 4));
        for (JButton button : moveButtons) {
            movesPanel.add(button);
        }
        mainPanel.add(movesPanel);
        add(mainPanel);
    }

    private class MoveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton sourceButton = (JButton) e.getSource();
            String moveName = sourceButton.getText();
            // Implement logic to handle move selection
            System.out.println("Player selected move: " + moveName);
            // Update game state accordingly
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PokemonGameInterface gameInterface = new PokemonGameInterface();
            gameInterface.setVisible(true);
        });
    }
}
