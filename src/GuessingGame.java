// Import necessary libraries
import javax.swing.*;     // For GUI components
import java.awt.*;        // For layout and design
import java.util.Random;  // For random number generation

// Main class extending JFrame to create a GUI window
public class GuessingGame extends JFrame
{
    // -------------------- Game State Variables --------------------
    private int randomNumber;            // The number to be guessed
    private int attempts;                // Number of attempts used
    private final int maxAttempts = 10;  // Maximum allowed attempts

    // -------------------- GUI Components --------------------
    private final JTextField guessField;               // Input field for user guesses
    private final JLabel feedbackLabel, attemptLabel;  // Displays feedback (correct, higher, lower, etc.)  // Displays remaining attempts
    private final JComboBox<String> difficultyBox;     // Dropdown for difficulty selection

    // -------------------- Constructor --------------------
    public GuessingGame()
    {
        // Window setup
        setTitle("AI Guessing Game");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Difficulty levels
        String[] levels = {"Easy (1-10)", "Medium (1-50)", "Hard (1-100)"};
        difficultyBox = new JComboBox<>(levels);

        // Input and buttons
        guessField = new JTextField(10);
        JButton guessButton = new JButton("Guess");
        JButton restartButton = new JButton("Restart");

        // Labels for feedback and attempts
        feedbackLabel = new JLabel("Select difficulty and Start!");
        attemptLabel = new JLabel("Attempts left: " + maxAttempts);

        // Add components to the window
        add(new JLabel("Difficulty:"));
        add(difficultyBox);
        add(new JLabel("Enter your guess:"));
        add(guessField);
        add(guessButton);
        add(feedbackLabel);
        add(attemptLabel);
        add(restartButton);

        // Initialize game state
        initGame();

        // Event listeners for buttons
        guessButton.addActionListener(_ -> handleGuess()); // Handle guesses
        restartButton.addActionListener(_ -> initGame());  // Restart game

        // Make window visible
        setVisible(true);
    }

    // -------------------- Game Initialization --------------------
    private void initGame()
    {
        Random rand = new Random();
        String selected = (String) difficultyBox.getSelectedItem();

        assert selected != null; // Ensure difficulty is selected

        // Set random number based on difficulty
        if (selected.contains("10")) randomNumber = rand.nextInt(10) + 1;
        else if (selected.contains("50")) randomNumber = rand.nextInt(50) + 1;
        else randomNumber = rand.nextInt(100) + 1;

        // Reset attempts and update UI
        attempts = 0;
        feedbackLabel.setText("New game started!");
        attemptLabel.setText("Attempts left: " + maxAttempts);
        guessField.setText("");
    }

    // -------------------- Guess Handling Logic --------------------
    private void handleGuess()
    {
        try
        {
            // Parse user input
            int userGuess = Integer.parseInt(guessField.getText());
            attempts++;
            int remaining = maxAttempts - attempts;

            // Case 1: Correct guess
            if (userGuess == randomNumber)
            {
                feedbackLabel.setText("Correct! You won in " + attempts + " tries.");
            }
            // Case 2: Out of attempts
            else if (remaining <= 0)
            {
                feedbackLabel.setText("Game Over! The number was " + randomNumber);
            }
            // Case 3: Wrong guess but attempts remain
            else
            {
                String hint = (userGuess < randomNumber) ? "Higher" : "Lower";
                // Adaptive hint: if guess is close
                if (Math.abs(userGuess-randomNumber) <= 3) hint += "(You're very close!)";
                feedbackLabel.setText(hint);
                attemptLabel.setText("Attempts left: " + remaining);
            }
        }
        catch (NumberFormatException ex)
        {
            // Handle invalid input (non-numeric values)
            JOptionPane.showMessageDialog(this, "Please enter a valid number!", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // -------------------- Main Method --------------------
    static void main()
    {
        try
        {
            // Set system look and feel for native UI appearance
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            throw new RuntimeException(e);
        }

        // Launch the game
        new GuessingGame();
    }
}