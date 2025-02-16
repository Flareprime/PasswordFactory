/*  Jordan Bassett
    Lizbeth Garcia-Lopez
    Password Factory
    CS236 - Advanced OOP (Java 2)
    Final Project: Strong Password Creation Game for Kids
    Updated circa February 16, 2025

    Description:
    This is the main entry point for the Password Factory game. Uses JavaFX
    It initializes the GUI with a title screen that includes options to:
      - Play the Game
      - Enter the Password Lab
      - Exit the Application

    The game ( is supposed to) teach kids about strong password creation through interactive gameplay
*/

package passwordfactory;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/*
  The App class initializes the main menu for the Password Factory program
  It provides navigation options to start the game, access the Password Lab,
    or exit the application.
 */
public class App extends Application
{
    // Initializes and displays the main menu window.
    // @param primaryStage The primary stage (window) for the program
    @Override
    public void start(Stage primaryStage)
    {
        //Title Label for the Main Menu
        Label titleLabel = new Label("Password Factory");

        //Buttons
        Button playGameButton = new Button("Play Game");
        Button passwordLabButton = new Button("Password Lab");
        Button exitButton = new Button("Exit");

        //Button Actions (Currently placeholder actions, outputs to console)
        playGameButton.setOnAction(e -> System.out.println("Play Game clicked"));
        passwordLabButton.setOnAction(e -> System.out.println("Password Lab clicked"));
        exitButton.setOnAction(e ->
        {
            System.out.println("Exiting game...");
            primaryStage.close(); //Closes the window
        });

        //Layout: Vertical Box (VBox) with spacing
        VBox root = new VBox(15, titleLabel, playGameButton, passwordLabButton, exitButton);
        root.setStyle("-fx-padding: 20px; -fx-alignment: center;"); //Center alignment and padding

        //Scene and Stage setup
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Password Game");
        primaryStage.setScene(scene);
        primaryStage.show(); // Display the window
    }

    /*main method launches the JavaFX application.
     @param args Command-line arguments (not used)
    */
    public static void main(String[] args)
    {
        launch(args); // Starts the JavaFX application
    }
}
