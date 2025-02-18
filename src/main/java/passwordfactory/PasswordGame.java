/*  Lizbeth Garcia-Lopez & Jordan Bassett   
    Password Factory
    CS236 - Advanced OOP (Java 2)
    Final Project: Strong Password Creation Game for Kids
    Updated circa February 17, 2025
    Desc: This class handles the core gameplay for Password Factory.
    Players enter a password within a time limit, and the game evaluates its strength.
    The game also provides feedback and scoring based on password quality.
    - Displays a password entry field.
    - Validates passwords using PasswordValidator.java.
    - Provides feedback on password strength (Weak, Moderate, Strong).
    - Tracks difficulty level and adjusts game settings accordingly.
    - Allows returning to the main menu.

    - Called from: GameSetupScreen.java (when "Start Game" is clicked).
    - Uses: PasswordValidator.java to check password strength.
    - Returns to: App.java (when "Back to Main Menu" is clicked).
*/
package passwordfactory;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PasswordGame
{
    private String difficulty;  //stores the selected difficulty level
    private Scene gameScene;    //object for the game screen
    private Stage gameStage;    //stores reference to the game window

    /* Constructor for PasswordGame.
     Initializes the game screen, sets up password input, and attaches event handlers
     @param difficulty The difficulty level chosen by the player. */
    public PasswordGame(String difficulty)
    {
        this.difficulty = difficulty;  //store selected difficulty level

        //Label to instruct the player
        Label instructionLabel = new Label("Enter a strong password (" + difficulty + " Mode):");

        //text field for password input
        TextField passwordField = new TextField();

        //Button to check password strength
        Button checkButton = new Button("Check Strength");

        //label to display strength evaluation result
        Label resultLabel = new Label();

        //button to return to the main menu
        Button backButton = new Button("Back to Menu");

        // Event handler for the check button
        checkButton.setOnAction(e ->
        {
            String password = passwordField.getText();  //get user input
            int strength = new PasswordValidator().checkStrength(password);  //check strength

            //assigns the appropriate message based on strength
            String message;
            switch (strength)
            {
                case 2:
                    message = "Strong";
                    break;
                case 1:
                    message = "Moderate";
                    break;
                default:
                    message = "Weak";
                    break;
            }

            //show the password strength evaluation
            resultLabel.setText(message);
        });

        //Event handler for returning to the main menu
        backButton.setOnAction(e ->
        {
            System.out.println("Returning to Main Menu..."); // Debugging output
            gameStage.close(); // ✅ Close the game window before opening the main menu
            new App().start(new Stage()); // ✅ Open a new main menu window
        });

        // Layout - organizes UI elements in a vertical box with spacing
        VBox layout = new VBox(10, instructionLabel, passwordField, checkButton, resultLabel, backButton);
        layout.setStyle("-fx-padding: 20px; -fx-alignment: center;");

        //Create the scene for this game screen
        gameScene = new Scene(layout, 400, 250);
    }

    //Displays the game screen.
    //Creates a new stage and sets its scene.
    public void show()
    {
        gameStage = new Stage(); // ✅ Store reference to the game window
        gameStage.setTitle("Password Factory - " + difficulty + " Mode");
        gameStage.setScene(gameScene);
        gameStage.show();
    }
}
