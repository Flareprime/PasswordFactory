package passwordfactory;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PasswordGame {
    private String difficulty;
    private Scene gameScene;
    private Stage gameStage;

    private int roundTime = 60;
    private int timeLeft;
    private int score = 0;
    private int passwordsSubmitted = 0;

    private Label timerLabel;
    private Label scoreLabel;
    private Label resultLabel;
    private Label hintLabel;

    private Timeline timer;

    private TextField passwordEntryField;
    private ListView<String> passwordHistoryView;

    private ListView<String> phraseListView;
    private BufferedWriter fileWriter;

    private Random random = new Random();
    private String phraseFile = "phrases.txt";

    private PasswordChecker passwordChecker;

    public PasswordGame(String difficulty) {
        this.difficulty = difficulty;
        this.timeLeft = roundTime;

        passwordChecker = new PasswordValidator();

        initPasswordLogFile();

        Label instructionLabel = new Label("Create as many strong passwords as you can in " + roundTime + " seconds!");

        passwordEntryField = new TextField();
        passwordEntryField.setPromptText("Enter your password");
        passwordEntryField.setOnKeyReleased(e -> updateHints(passwordEntryField.getText()));

        Button submitButton = new Button("Submit Password");
        submitButton.setOnAction(e -> submitPassword());

        hintLabel = new Label("Hints will appear here as you type.");
        hintLabel.setWrapText(true);
        hintLabel.setMaxWidth(400);

        timerLabel = new Label("Time Left: " + timeLeft + "s");
        scoreLabel = new Label("Score: " + score);
        resultLabel = new Label();

        // Password history list (Right Side)
        passwordHistoryView = new ListView<>();
        passwordHistoryView.setPrefWidth(400);  // Made wider

        // Phrase list on Left Side
        List<String> phrases = loadPhrasesFromFile(phraseFile);
        phraseListView = new ListView<>();
        phraseListView.getItems().addAll(phrases);
        phraseListView.setPrefWidth(200);
        phraseListView.setOnMouseClicked(e -> appendPhraseToPassword());

        TextField newPhraseField = new TextField();
        newPhraseField.setPromptText("Enter new phrase");
        Button addPhraseButton = new Button("Add Phrase");
        addPhraseButton.setOnAction(e -> {
            addNewPhrase(newPhraseField.getText());
            newPhraseField.clear();
        });

        Button backButton = new Button("Back to Menu");
        backButton.setOnAction(e -> {
            timer.stop();
            try {
                if (fileWriter != null) fileWriter.close();
            } catch (IOException ex) {
                System.out.println("Error closing file writer.");
            }
            gameStage.close();
            new App().start(new Stage());
        });

        VBox leftSide = new VBox(10,
                new Label("Helpful Phrases:"),
                phraseListView,
                newPhraseField,
                addPhraseButton,
                backButton);
        leftSide.setPadding(new Insets(10));
        leftSide.setStyle("-fx-alignment: top-center;");

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

        VBox rightSide = new VBox(10,
                new Label("Passwords This Round:"),
                passwordHistoryView);
        rightSide.setPadding(new Insets(10));
        rightSide.setStyle("-fx-alignment: top-left;");

        // Increased window size and spacing
        HBox mainLayout = new HBox(30, leftSide, centerPane, rightSide);
        mainLayout.setPadding(new Insets(10));

        gameScene = new Scene(mainLayout, 1100, 600);  // Wider and taller window
    }

    public void show() {
        gameStage = new Stage();
        gameStage.setTitle("Password Factory - Time Attack Mode");
        gameStage.setScene(gameScene);
        gameStage.setResizable(false);
        gameStage.show();
        startRoundTimer();
    }

    private void startRoundTimer() {
        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeLeft--;
            timerLabel.setText("Time Left: " + timeLeft + "s");

            if (timeLeft <= 0) {
                timer.stop();
                endRound();
            }
        }));
        timer.setCycleCount(roundTime);
        timer.play();
    }

    private void submitPassword() {
        String password = passwordEntryField.getText();

        if (password == null || password.isEmpty()) {
            resultLabel.setText("\u26A0 Enter a password first!");
            return;
        }

        // ✅ Strip spaces from passwords automatically
        password = password.replaceAll("\\s+", "");

        int strength = passwordChecker.checkStrength(password);
        String feedback = passwordChecker.giveFeedback(password);

        String strengthLabel;
        switch (strength) {
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
                break;
        }

        passwordsSubmitted++;
        resultLabel.setText("Password Strength: " + feedback);
        scoreLabel.setText("Score: " + score);

        // ✅ Add multiline entry to history
        String historyEntry = "Password: " + password + "\n"
                            + "Strength: " + strengthLabel + "\n"
                            + "Hint: " + feedback;
        passwordHistoryView.getItems().add(historyEntry);

        savePasswordToFile(password, strengthLabel);

        passwordEntryField.clear();
        hintLabel.setText("");
        updateHints("");
    }

    private void updateHints(String password) {
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

    private void endRound() {
        passwordEntryField.setDisable(true);

        resultLabel.setText("\uD83C\uDFC6 Time's Up! Final Score: " + score + " | Passwords Submitted: " + passwordsSubmitted);
        timerLabel.setText("Round Over");

        try {
            if (fileWriter != null) fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error closing password log file.");
        }
    }

    private void initPasswordLogFile() {
        try {
            fileWriter = new BufferedWriter(new FileWriter("password_log.txt", false));
            fileWriter.write("Password Factory Log - Time Attack Mode\n\n");
        } catch (IOException e) {
            System.out.println("Error initializing password log file.");
        }
    }

    private void savePasswordToFile(String password, String strength) {
        try {
            fileWriter.write("Password: " + password + " | Strength: " + strength + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to password log file.");
        }
    }

    private List<String> loadPhrasesFromFile(String filename) {
        List<String> phrases = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                phrases.add(line);
            }
        } catch (IOException e) {
            phrases.add("Default Phrase 1");
            phrases.add("Default Phrase 2");
            System.out.println("Error reading " + filename + ". Using default phrases.");
        }

        return phrases;
    }

    private void addNewPhrase(String newPhrase) {
        if (newPhrase == null || newPhrase.trim().isEmpty()) {
            resultLabel.setText("\u26A0 Enter a valid phrase!");
            return;
        }

        try (FileWriter writer = new FileWriter(phraseFile, true)) {
            writer.write(newPhrase.trim() + "\n");
            phraseListView.getItems().add(newPhrase.trim());
            resultLabel.setText("Phrase added successfully!");
        } catch (IOException e) {
            resultLabel.setText("\u26A0 Error adding phrase.");
        }
    }

    private void appendPhraseToPassword() {
        String selectedPhrase = phraseListView.getSelectionModel().getSelectedItem();
        if (selectedPhrase != null) {
            String currentText = passwordEntryField.getText();
            passwordEntryField.setText(currentText + selectedPhrase.replaceAll("\\s", ""));
        }
    }
}
