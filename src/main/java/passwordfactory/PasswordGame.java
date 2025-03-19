/* Password Factory Project
 CS236 - Advanced OOP (Java 2)
 Class: PasswordGame.java
 Author(s): Jordan Bassett & Lizbeth Garcia-Lopez
 Last Updated: March 16, 2025
 
 Description:
    Manages the main gameplay loop for Password Factory. Handles user input, scoring,
    timer management, and password validation during a timed round of the game.

 Features:
    - Runs a timed challenge to create strong passwords.
    - Provides real-time hints based on password input.
    - Displays helpful phrases and allows the user to add new phrases.
    - Tracks and saves submitted passwords and their strength ratings.
 
 Dependencies:
    - Uses PasswordValidator for password evaluation.
    - Uses PasswordFileManager for file operations (phrases and logging).
    - Called from: GameSetupScreen.java
 */
package passwordfactory;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.List;
import java.util.Random;

//Manages the main gameplay loop, user interactions, and password validation
public class PasswordGame
{
 // Class variables
    private String difficulty;    //difficulty level selected (Easy, Medium, Hard)
    private final Scene gameScene;      //Main scene for the game window
    private Stage gameStage;      // Game window stage

    private final int roundTime;            //Total time for the round, based on difficulty
    private int timeLeft;             //Countdown timer for the current round
    private int score = 0;            //player score, based on password strength
    private int passwordsSubmitted = 0;  // Number of passwords submitted this round

    // UI Labels for displaying game status
    private final Label timerLabel;         //Displays time left in the round
    private final Label scoreLabel;         //Displays current score
    private Label resultLabel;        //Displays strength feedback for the last password
    private final Label hintLabel;          //Displays hints for improving the password

    private Timeline timer;     // Timer for the countdown (JavaFX Timeline)

    //User input and output components
    private TextField passwordEntryField;         //Field where user types passwords
    private final ListView<String> passwordHistoryView;  //Shows passwords submitted in this round
    private ListView<String> phraseListView;       //Displays helpful phrases to add to passwords

    //Random number generator (currently unused)
    //private Random random = new Random();

    //Core components
    private final PasswordChecker passwordChecker;   //Password strength evaluator (PasswordValidator)
    private PasswordFileManager fileManager;      //Manages phrase/common word files and password logs


    /**
     * Constructor for PasswordGame.
     * Initializes the game UI and sets up game logic based on difficulty.
     * @param difficulty The selected difficulty level (Easy, Medium, Hard).
     */
    public PasswordGame(String difficulty)
    {
        this.difficulty = difficulty;

        // Set round time based on difficulty level
        switch (difficulty.toLowerCase())
        {
            case "easy":
                roundTime = 60;
                break;
            case "medium":
                roundTime = 45;
                break;
            case "hard":
                roundTime = 30;
                break;
            default:
                roundTime = 60;
        }
        this.timeLeft = roundTime;

        //Initialize file manager and password validator
        fileManager = new PasswordFileManager("phrases.txt", "password_log.txt");
        fileManager.initPasswordLog();

        passwordChecker = new PasswordValidator(fileManager, difficulty);

        //instruction label at the top of the game screen
        Label instructionLabel = new Label("Create as many strong passwords as you can in " + roundTime + " seconds!");

        //Password input field with real-time hint updates
        passwordEntryField = new TextField();
        passwordEntryField.setPromptText("Enter your password");
        passwordEntryField.setOnKeyReleased(e -> updateHints(passwordEntryField.getText()));

        //Submit button for submitting passwords
        Button submitButton = new Button("Submit Password");
        submitButton.setOnAction(e -> submitPassword());

        //labels for displaying hints, timer, score, and result feedback
        hintLabel = new Label("Hints will appear here as you type.");
        hintLabel.setWrapText(true);
        hintLabel.setMaxWidth(400);

        timerLabel = new Label("Time Left: " + timeLeft + "s");
        scoreLabel = new Label("Score: " + score);

        resultLabel = new Label();
        resultLabel.setWrapText(true);
        resultLabel.setMaxWidth(400);

        // shows submitted passwords for this round
        passwordHistoryView = new ListView<>();
        passwordHistoryView.setPrefWidth(400);

        //shows 'helpful' phrases and allows adding new ones
        List<String> phrases = fileManager.loadPhrases();
        phraseListView = new ListView<>();
        phraseListView.getItems().addAll(phrases);
        phraseListView.setPrefWidth(200);
        phraseListView.setOnMouseClicked(e -> appendPhraseToPassword());

        TextField newPhraseField = new TextField();
        newPhraseField.setPromptText("Enter new phrase");

        Button addPhraseButton = new Button("Add Phrase");
        addPhraseButton.setOnAction(e ->
        {
            boolean success = fileManager.addPhrase(newPhraseField.getText());
            if (success)
            {
                phraseListView.getItems().add(newPhraseField.getText().trim());
                resultLabel.setText("Phrase added successfully!");
                newPhraseField.clear();
            }
            else {
                resultLabel.setText("\u26A0 Enter a valid phrase!");
            }
        });

        Button backButton = new Button("Back to Menu");
        backButton.setOnAction(e ->
        {
            timer.stop();
            fileManager.closeLog();
            gameStage.close();
            new App().start(new Stage());
        });

        //Left side: Helpful phrases and phrase adding UI
        VBox leftSide = new VBox(10,
            new Label("Helpful Phrases:"),
            phraseListView,
            newPhraseField,
            addPhraseButton,
            backButton);
        leftSide.setPadding(new Insets(10));
        leftSide.setStyle("-fx-alignment: top-center;");

        //Center pane: Instructions, password entry, submit, feedback, and score
        VBox centerPane = new VBox(10,
            instructionLabel,
            passwordEntryField,
            submitButton,
            hintLabel,
            resultLabel,
            timerLabel,
            scoreLabel);
        centerPane.setPadding(new Insets(20));
        centerPane.setStyle("-fx-alignment: center;");

        //Right side: Passwords submitted this round
        VBox rightSide = new VBox(10,
            new Label("Passwords This Round:"),
            passwordHistoryView);
        rightSide.setPadding(new Insets(10));
        rightSide.setStyle("-fx-alignment: top-left;");

        //Combine all sections into the main layout
        HBox mainLayout = new HBox(30, leftSide, centerPane, rightSide);
        mainLayout.setPadding(new Insets(10));
        gameScene = new Scene(mainLayout, 1100, 600);
    }

    // Displays the game window and starts the round timer.
    public void show()
    {
        gameStage = new Stage();
        gameStage.setTitle("Password Factory - Time Attack Mode");
        gameStage.setScene(gameScene);
        gameStage.setResizable(false);
        gameStage.setWidth(1100);
        gameStage.setHeight(600);
        gameStage.show();
        startRoundTimer();
    }

    //Starts the countdown timer for the game round.
     // Updates the timer label and ends the round when time runs out.
    private void startRoundTimer()
    {
        timer = new Timeline(new KeyFrame(Duration.seconds(1), e ->
        {
            timeLeft--;
            timerLabel.setText("Time Left: " + timeLeft + "s");

            if (timeLeft <= 0)
            {
                timer.stop();
                endRound();
            }
        }));
        timer.setCycleCount(roundTime);
        timer.play();
    }

    /* Handles password submission, evaluates strength, updates score,
      and logs the password with its feedback.    */
    private void submitPassword()
    {
        String password = passwordEntryField.getText();

        if (password == null || password.isEmpty())
        {
            resultLabel.setText("\u26A0 Enter a password first!");
            return;
        }

        password = password.replaceAll("\\s+", ""); // Strip spaces

        int strength = passwordChecker.checkStrength(password);
        String feedback = passwordChecker.giveFeedback(password);

        String strengthLabel;
        switch (strength)
        {
            case 2:
                strengthLabel = "Strong";
                score += 10;
                break;
            case 1:
                strengthLabel = "Moderate";
                score += 5;
                break;
            default:
                strengthLabel = "Weak";
                score += 0;
        }

        passwordsSubmitted++;
        resultLabel.setText("Password Strength: " + strengthLabel + "\n" + feedback);
        scoreLabel.setText("Score: " + score);

        String historyEntry = "Password: " + password + "\n"
                            + "Strength: " + strengthLabel + "\n"
                            + feedback;

        passwordHistoryView.getItems().add(historyEntry);

        fileManager.savePassword(password, strengthLabel);

        passwordEntryField.clear();
        hintLabel.setText("");
    }

    /**
     * Updates hints in real time as the user types a password.
     * Provides tips on improving the password's strength.
     * @param password The password being entered.
     */
    private void updateHints(String password)
    {
        StringBuilder hints = new StringBuilder();

        if (password.length() < 8) {
            hints.append("Try making it longer. ");
        }

        if (!password.matches(".*[A-Z].*")) {
            hints.append("Add an uppercase letter. ");
        }

        if (!password.matches(".*[a-z].*")) {
            hints.append("Add a lowercase letter. ");
        }

        if (!password.matches(".*\\d.*")) {
            hints.append("Add a number. ");
        }

        if (!password.matches(".*[!@#$%&*].*")) {
            hints.append("Add a special character. ");
        }

        if (hints.length() == 0) {
            hints.append("Looks good so far!");
        }

        hintLabel.setText(hints.toString());
    }

    
    //ends the round, disables input, displays final score, and closes the log file
    private void endRound()
    {
        passwordEntryField.setDisable(true);
        resultLabel.setText("\uD83C\uDFC6 Time's Up! Final Score: " + score
            + " | Passwords Submitted: " + passwordsSubmitted);
        timerLabel.setText("Round Over");
        fileManager.closeLog();
    }

    //appends a selected helpful phrase to the password input field
    private void appendPhraseToPassword()
    {
        String selectedPhrase = phraseListView.getSelectionModel().getSelectedItem();

        if (selectedPhrase != null)
        {
            String currentText = passwordEntryField.getText();
            passwordEntryField.setText(currentText + selectedPhrase.replaceAll("\\s", ""));
        }
    }
}
