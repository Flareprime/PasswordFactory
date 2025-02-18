/*  Jordan Bassett & Lizbeth Garcia-Lopez
    Password Factory
    CS236 - Advanced OOP (Java 2)
    Final Project: Strong Password Creation Game for Kids
    Updated circa February 17, 2025

    Description:
    This is the main entry point for the Password Factory game. Uses JavaFX.
    It initializes the GUI with a title screen that includes options to:
      - Play the Game
      - Enter the Password Lab
      - Exit the Application
      - Open the "About" screen with system info (tiny button in bottom-right).
    
    The game (is supposed to) teach kids about strong password creation through interactive gameplay.

    Navigation:
    - Calls: GameSetupScreen.java (when "Play Game" is clicked)
    - Calls: AboutScreen.java (when "?" button is clicked)
    - Returns to: App.java (when going back to the main menu)
*/
package passwordfactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Main application entry point for Password Factory.
 * Initializes the main menu GUI
 */
public class App extends Application
{
    /**
     * Initializes and displays the main menu window.
     * @param primaryStage The primary stage (window) for the program.
     */
@Override
public void start(Stage primaryStage)
{
    // Title Label for the Main Menu
    Label titleLabel = new Label("Password Factory");

    // Main Menu Buttons
    Button playGameButton = new Button("Play Game");
    Button passwordLabButton = new Button("Password Lab");
    Button exitButton = new Button("Exit");

    // Button Actions
    playGameButton.setOnAction(e -> 
    {
        System.out.println("Play Game button clicked"); // Debugging output
        GameSetupScreen setupScreen = new GameSetupScreen(primaryStage);
        setupScreen.show();
    });

    passwordLabButton.setOnAction(e -> 
    {
        System.out.println("Password Lab button clicked"); // Debugging output
    });

    exitButton.setOnAction(e -> 
    {
        System.out.println("Exiting application..."); // Debugging output
        primaryStage.close();
    });

    // VBox for main menu buttons (Centered)
    VBox mainButtons = new VBox(15, titleLabel, playGameButton, passwordLabButton, exitButton);
    mainButtons.setStyle("-fx-padding: 20px; -fx-alignment: center;");

    // Small "About" button (bottom-right corner)
    Button aboutButton = new Button("?");
    aboutButton.setStyle("-fx-font-size: 10px; -fx-padding: 5px; -fx-background-radius: 50%;");
    aboutButton.setOnAction(e -> new AboutScreen().show());

    // Use an HBox to align the "About" button to the bottom-right
    HBox aboutContainer = new HBox(aboutButton);
    aboutContainer.setStyle("-fx-alignment: bottom-right; -fx-padding: 10px;");
    
    // Use BorderPane to keep layout structured
    BorderPane root = new BorderPane();
    root.setCenter(mainButtons); // Main buttons stay interactive in the center
    root.setBottom(aboutContainer); // About button stays at the bottom

    // Scene and Stage setup
    Scene scene = new Scene(root, 400, 300);
    primaryStage.setTitle("Password Factory");
    primaryStage.setScene(scene);
    primaryStage.show();
}

    /**
     * Main method to launch the JavaFX application.
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args)
    {
        launch(args); // Starts the JavaFX application
    }
}
