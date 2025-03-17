/* Password Factory Project
 CS236 - Advanced OOP (Java 2)
 Class: PasswordLab.java
 Author(s): Lizbeth Garcia-Lopez & Jordan Bassett
 Last Updated: March 16, 2025
 
 Description:
    Password Lab is the sandbox mode for Password Factory. It allows users to test
    passwords and edit helpful phrases and common words. The Lab provides feedback
    on password strength in real-time and saves user edits to the corresponding text files

 Features:
    - Tests passwords and provides feedback using PasswordValidator
    - Allows adding, removing, and saving common words
    - Allows adding, removing, and saving helpful phrases
    - Provides an interface to return to the main menu
 
 Dependencies:
    - Uses: PasswordValidator.java, PasswordFileManager.java, App.java
 */
package passwordfactory;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.List;

//Password Lab provides an interface for testing passwords and managing common words and phrases.
public class PasswordLab
{

    // Stage and Scene Elements
    private Stage labStage;                    //window for Password Lab

    // Core Components
    private PasswordFileManager fileManager;   //file operations (phrases, common words, logs)
    private PasswordValidator validator;       //Validates passwords and provides feedback

    // UI Components - Lists
    private ListView<String> commonWordsList;  //List of common words (editable)
    private ListView<String> phrasesList;      //List of helpful phrases (editable)

    //UI Components - Password Testing
    private TextField passwordTestField;       //Field for entering passwords to test
    private Label testResultLabel;             //shows the strength rating of the tested password
    private Label feedbackLabel;               //shows detailed feedback and suggestions

    /**
     * Constructor for PasswordLab.
     * Initializes the validator and file manager.
     */
    public PasswordLab()
    {
        //Initialize file manager and validator (Easy mode for testing)
        fileManager = new PasswordFileManager("phrases.txt", "password_log.txt");
        validator = new PasswordValidator(fileManager, "easy");
    }

    /**
     * Displays the Password Lab window.
     * Provides UI for testing passwords and editing common words and helpful phrases.
     */
    public void showLab()
    {
        labStage = new Stage();
        labStage.setTitle("Password Lab");

        // Password Tester Section
        Label testHeader = new Label("Test a Password");
        passwordTestField = new TextField();
        passwordTestField.setPromptText("Enter a password to test...");

        Button testButton = new Button("Test Password");
        testButton.setOnAction(e -> testPassword());

        testResultLabel = new Label();
        feedbackLabel = new Label();
        feedbackLabel.setWrapText(true);
        feedbackLabel.setMaxWidth(400);

        VBox testBox = new VBox(10,
            testHeader,
            passwordTestField,
            testButton,
            testResultLabel,
            feedbackLabel);
        testBox.setPadding(new Insets(10));
        testBox.setStyle("-fx-border-color: #aaa; -fx-border-radius: 5; -fx-padding: 10;");

        // Common Words Section
        Label commonWordsHeader = new Label("Common Words");
        commonWordsList = new ListView<>();
        commonWordsList.getItems().addAll(fileManager.loadCommonWords("common_words.txt"));

        TextField addCommonWordField = new TextField();
        addCommonWordField.setPromptText("Add common word...");

        Button addCommonWordButton = new Button("Add");
        addCommonWordButton.setOnAction(e ->
        {
            String word = addCommonWordField.getText().trim().toLowerCase();

            if (!word.isEmpty() && !commonWordsList.getItems().contains(word))
            {
                commonWordsList.getItems().add(word);
                addCommonWordField.clear();
            }
        });

        Button removeCommonWordButton = new Button("Remove Selected");
        removeCommonWordButton.setOnAction(e ->
        {
            String selected = commonWordsList.getSelectionModel().getSelectedItem();

            if (selected != null) {
                commonWordsList.getItems().remove(selected);
            }
        });

        Button saveCommonWordsButton = new Button("Save Common Words");
        saveCommonWordsButton.setOnAction(e -> saveCommonWords());

        VBox commonWordsBox = new VBox(10,
            commonWordsHeader,
            commonWordsList,
            addCommonWordField,
            addCommonWordButton,
            removeCommonWordButton,
            saveCommonWordsButton);
        commonWordsBox.setPadding(new Insets(10));
        commonWordsBox.setStyle("-fx-border-color: #aaa; -fx-border-radius: 5; -fx-padding: 10;");
        commonWordsBox.setPrefWidth(300);

        // Helpful Phrases Section
        Label phrasesHeader = new Label("Helpful Phrases");
        phrasesList = new ListView<>();
        phrasesList.getItems().addAll(fileManager.loadPhrases());

        TextField addPhraseField = new TextField();
        addPhraseField.setPromptText("Add helpful phrase...");

        Button addPhraseButton = new Button("Add");
        addPhraseButton.setOnAction(e ->
        {
            String phrase = addPhraseField.getText().trim();

            if (!phrase.isEmpty() && !phrasesList.getItems().contains(phrase))
            {
                phrasesList.getItems().add(phrase);
                addPhraseField.clear();
            }
        });

        Button removePhraseButton = new Button("Remove Selected");
        removePhraseButton.setOnAction(e ->
        {
            String selected = phrasesList.getSelectionModel().getSelectedItem();

            if (selected != null)
            {
                phrasesList.getItems().remove(selected);
            }
        });

        Button savePhrasesButton = new Button("Save Phrases");
        savePhrasesButton.setOnAction(e -> savePhrases());

        VBox phrasesBox = new VBox(10,
            phrasesHeader,
            phrasesList,
            addPhraseField,
            addPhraseButton,
            removePhraseButton,
            savePhrasesButton);
        phrasesBox.setPadding(new Insets(10));
        phrasesBox.setStyle("-fx-border-color: #aaa; -fx-border-radius: 5; -fx-padding: 10;");
        phrasesBox.setPrefWidth(300);

        // Back Button to return to main menu
        Button backButton = new Button("Back to Menu");
        backButton.setOnAction(e ->
        {
            labStage.close();
            new App().start(new Stage());
        });

        // Layout
        HBox mainLayout = new HBox(20,
            commonWordsBox,
            testBox,
            phrasesBox);

        VBox rootLayout = new VBox(10,
            mainLayout,
            backButton);

        rootLayout.setPadding(new Insets(10));
        rootLayout.setStyle("-fx-alignment: center;");

        Scene labScene = new Scene(rootLayout, 1100, 600);
        labStage.setScene(labScene);
        labStage.setResizable(false);
        labStage.show();
    }

    // Tests the entered password for strength and feedback
    private void testPassword()
    {
        String password = passwordTestField.getText().trim();

        if (password.isEmpty())
        {
            testResultLabel.setText("⚠ Enter a password to test.");
            feedbackLabel.setText("");
            return;
        }

        int strength = validator.checkStrength(password);
        String feedback = validator.giveFeedback(password);

        String strengthLabel;
        switch (strength)
        {
            case 2: strengthLabel = "Strong"; break;
            case 1: strengthLabel = "Moderate"; break;
            default: strengthLabel = "Weak"; break;
        }

        testResultLabel.setText("Strength: " + strengthLabel);
        feedbackLabel.setText(feedback);
    }

    //Saves edits to the common words list back to common_words.txt.
     //Refreshes the validator to use the updated common words.
    private void saveCommonWords()
    {
        List<String> words = commonWordsList.getItems();
        fileManager.saveCommonWords(words, "common_words.txt");

        //Refresh validator with updated common words
        validator = new PasswordValidator(fileManager, "easy");

        testResultLabel.setText("✅ Common words saved.");
    }

    //saves edits to the helpful phrases list back to phrases.txt.
    private void savePhrases()
    {
        List<String> phrases = phrasesList.getItems();
        fileManager.savePhrases(phrases, "phrases.txt");
        testResultLabel.setText("✅ Phrases saved.");
    }
}
