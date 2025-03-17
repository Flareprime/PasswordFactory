/* Password Factory Project
 CS236 - Advanced OOP (Java 2)
 Class: App.java
 Author(s): Jordan Bassett & Lizbeth Garcia-Lopez
 Last Updated: March 16, 2025
 
 Description:
    This is the main entry point for the Password Factory game. It launches the JavaFX application,
    initializes the main menu, and handles navigation to the Password Game, Password Lab, and About Screen.

 Features:
  - Displays the main menu with buttons for Play Game, Password Lab, and Exit
  - Opens the GameSetupScreen for selecting difficulty
  - Opens PasswordLab for password testing and file editing
  - Opens AboutScreen to show system information
 
 Dependencies:
    - Calls: GameSetupScreen.java, PasswordLab.java, AboutScreen.java
    - Launches the Password Factory application with JavaFX Application class  */
package passwordfactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

//Initializes and displays the main menu GUI.
public class App extends Application
{
     //@param primaryStage The primary stage (window) for the program.
     //@param primaryStage
    @Override
    public void start(Stage primaryStage)
    {
        //Title for main menu
        Label titleLabel = new Label("Password Factory");

        //Main menu buttons
        Button playGameButton = new Button("Play Game");
        Button passwordLabButton = new Button("Password Lab");
        Button exitButton = new Button("Exit");

        //play Game Button Action
        playGameButton.setOnAction(e -> 
        {
            System.out.println("Play Game button clicked"); //Debugging output
            GameSetupScreen setupScreen = new GameSetupScreen(primaryStage);
            setupScreen.show();
        });

        //Password Lab Button Action
        passwordLabButton.setOnAction(e -> 
        {
            System.out.println("Password Lab button clicked"); //Debugging output

            // Launch Password Lab
            PasswordLab passwordLab = new PasswordLab();
            passwordLab.showLab();

            // Close the main menu
            primaryStage.close();
        });

        //Exit Button Action
        exitButton.setOnAction(e -> 
        {
            System.out.println("Exiting application..."); //debugging output
            primaryStage.close();
        });

        //VBox for main menu buttons (Centered vertically)
        VBox mainButtons = new VBox(15, titleLabel, playGameButton, passwordLabButton, exitButton);
        mainButtons.setStyle("-fx-padding: 20px; -fx-alignment: center;");

        //Small "About" button in the bottom-right corner
        Button aboutButton = new Button("?");
        aboutButton.setStyle("-fx-font-size: 10px; -fx-padding: 5px; -fx-background-radius: 50%;");

        //About Button Action
        aboutButton.setOnAction(e -> new AboutScreen().show());

        //container to align the about button to the bottom-right
        HBox aboutContainer = new HBox(aboutButton);
        aboutContainer.setStyle("-fx-alignment: bottom-right; -fx-padding: 10px;");

        //BorderPane layout for the main menu
        BorderPane root = new BorderPane();
        root.setCenter(mainButtons); // Main buttons in center
        root.setBottom(aboutContainer); // About button in bottom-right

        //Scene and stage setup
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Password Factory");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

     //Main method to launch the JavaFX application.
     //@param args Command-line arguments (not used).
    public static void main(String[] args) {
        launch(args); //starts the JavaFX app
    }
}
