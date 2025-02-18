/*  Lizbeth Garcia-Lopez & Jordan Bassett    
    Password Factory
    CS236 - Advanced OOP (Java 2)
    Final Project: Strong Password Creation Game for Kids
    Updated circa February 17, 2025

    Desc: This class creates the game setup screen where players select a difficulty level 
    before starting the password challenge. It provides instructions and handles transitions 
    between the main menu and the actual game screen.

    Features:
    - Displays game instructions.
    - Allows players to choose a difficulty level (Easy, Medium, Hard).
    - Starts the game when the player clicks "Start Game."
    - Provides a "Back to Main Menu" button to return to App.java.

    Navigation:
    - Called from: App.java (when "Play Game" is clicked).
    - Calls: PasswordGame.java (when "Start Game" is clicked).
*/
package passwordfactory;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality; //determines whether the user can interact with other windows while this new window is open
import javafx.stage.Stage;

public class GameSetupScreen 
{
    private Stage mainMenuStage;  //stores reference to the main menu stage to close it

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

        //create a new window/stage for the setup screen
        Stage setupStage = new Stage();
        //prevents the user from interacting with the main menu while the setup screen is open
        setupStage.initModality(Modality.APPLICATION_MODAL); 
        setupStage.setTitle("Game Setup");
        // Debugging output
        System.out.println("Setup screen should be visible now");
        //Instructions label displaying basic game information
        Label instructions = new Label(
            "Welcome to Password Factory! \n\n"
            + "Your goal is to create the strongest password possible.\n"
            + "Choose a difficulty level below to start.\n"
            + "More instructions will be here when we actually know how the game works!"
        );

        //Difficulty selection using radio buttons
        ToggleGroup difficultyGroup = new ToggleGroup();
        RadioButton easyButton = new RadioButton("Easy");
        RadioButton mediumButton = new RadioButton("Medium");
        RadioButton hardButton = new RadioButton("Hard");

        //Assign buttons to the same toggle group so only one can be selected at a time
        easyButton.setToggleGroup(difficultyGroup);
        mediumButton.setToggleGroup(difficultyGroup);
        hardButton.setToggleGroup(difficultyGroup);
        
        easyButton.setSelected(true); //Default selection is Easy

        //start Game Button
        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> 
        {
            //find the selected difficulty
            String selectedDifficulty = ((RadioButton) difficultyGroup.getSelectedToggle()).getText();
            
            //close the setup window
            setupStage.close();

            //open the PasswordGame screen and send it selected difficulty
            new PasswordGame(selectedDifficulty).show();
        });

        //Back to Main Menu Button
        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(e -> 
        {
            //close the setup window
            setupStage.close();

            //tReopen the main menu
            Stage mainStage = new Stage();
            new App().start(mainStage);
        });

        //Layout setup: VBox with 10px spacing between elements
        VBox layout = new VBox(10, instructions, easyButton, mediumButton, hardButton, startButton, backButton);
        layout.setStyle("-fx-padding: 20px; -fx-alignment: center;");

        //Assign the scene to the stage and show the setup screen
        setupStage.setScene(new Scene(layout, 400, 300));
        setupStage.show();
    }
}
