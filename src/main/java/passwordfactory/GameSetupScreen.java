/*  Lizbeth Garcia-Lopez & Jordan Bassett    
    Password Factory
    CS236 - Advanced OOP (Java 2)
    Final Project: Strong Password Creation Game for Kids
    Updated March 16 2025

    Desc: This class creates the game setup screen where players select a difficulty level 
    before starting the password challenge. It provides instructions and handles transitions 
    between the main menu and the actual game screen.

    Features:
    - Displays game instructions.
    - Allows players to choose a difficulty level (Easy, Medium, Hard)
    - Starts the game when the player clicks "Start Game"
    - Provides a "Back to Main Menu" button to return to App.java

    Navigation:
    - Called from: App.java (when "Play Game" is clicked)
    - Calls: PasswordGame.java (when "Start Game" is clicked) */
package passwordfactory;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GameSetupScreen
{
    private final Stage mainMenuStage;  // stores reference to the main menu stage to close it

    /**
     * Constructor for GameSetupScreen
     * @param mainMenuStage Reference to the main menu stage so it can be closed when this screen opens
     */
    public GameSetupScreen(Stage mainMenuStage) {
        this.mainMenuStage = mainMenuStage;
    }

    //Displays the setup screen where players select their difficulty level
    public void show()
    {
        // Close the main menu when opening this setup screen
        if (mainMenuStage != null) {
            mainMenuStage.close();
    }

    // Create a new window/stage for the setup screen
    Stage setupStage = new Stage();

    //Prevent user from interacting with the main menu while the setup screen is open
    setupStage.initModality(Modality.APPLICATION_MODAL);
    setupStage.setTitle("Game Setup");

    // Debugging output
    //System.out.println("Setup screen should be visible now");

    
    // Instructions using TextFlow
    TextFlow instructionsFlow = new TextFlow();

    Text header = new Text("Welcome to Password Factory!\n\n");
    header.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");

        Text line1 = new Text("• Enter passwords as fast as you can before time runs out.\n");
        Text line2 = new Text("• Watch for hints to improve your password strength.\n");
        Text line3 = new Text("• Earn points for stronger passwords. Weak ones won't score!\n");
        Text line4 = new Text("• Avoid common words or risk losing points!\n\n");

        Text difficultyHeader = new Text("Difficulty Levels:\n");
        difficultyHeader.setStyle("-fx-font-weight: bold;");

        Text easyDesc = new Text("Easy: More time. Basic password rules.\n");
        Text mediumDesc = new Text("Medium: Less time. Common words are penalized harder.\n");
        Text hardDesc = new Text("Hard: Tight time. Only complex passwords score!\n");

        instructionsFlow.getChildren().addAll(
            header, line1, line2, line3, line4,
            difficultyHeader, easyDesc, mediumDesc, hardDesc
        );

    //Difficulty selection using radio buttons
     
        ToggleGroup difficultyGroup = new ToggleGroup();
        RadioButton easyButton = new RadioButton("Easy");
        RadioButton mediumButton = new RadioButton("Medium");
        RadioButton hardButton = new RadioButton("Hard");

        easyButton.setToggleGroup(difficultyGroup);
        mediumButton.setToggleGroup(difficultyGroup);
        hardButton.setToggleGroup(difficultyGroup);

        easyButton.setSelected(true); // Default selection is Easy

        //Start Game button
        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> {
        //find the selected difficulty
        String selectedDifficulty = ((RadioButton) difficultyGroup.getSelectedToggle()).getText();

        //close the setup window
        setupStage.close();

        //open the PasswordGame screen and send it selected difficulty
            new PasswordGame(selectedDifficulty).show();
        });

        //Back to Main Menu button
        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(e ->
        {
            setupStage.close();
            Stage mainStage = new Stage();
            new App().start(mainStage);
        });

        //Layout: VBox with spacing and padding
        VBox layout = new VBox(10,
            instructionsFlow,
            easyButton,
            mediumButton,
            hardButton,
            startButton,
            backButton
        );
        layout.setStyle("-fx-padding: 20px; -fx-alignment: center;");

        //assign the scene to the stage and show the setup screen
        setupStage.setScene(new Scene(layout, 500, 500));
        setupStage.show();
    }
}
