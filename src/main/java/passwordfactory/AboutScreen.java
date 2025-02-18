/*  Jordan Bassett & Lizbeth Garcia-Lopez
    Password Factory
    CS236 - Advanced OOP (Java 2)
    Final Project: Strong Password Creation Game for Kids
    Updated circa February 17, 2025
    Desc: This class creates a simple "About" screen that provides information about the game
    and displays system details, such as Java and JavaFX versions

    - Displays game title, version, and credits.
    - Uses SystemInfo.java to retrieve Java and JavaFX version details
    - Provides a "Close" button to dismiss the window

    - Called from: App.java (when the "?" button is clicked).
    - Uses SystemInfo.java (which Netbeans always includes) to retrieve system details.

    - doubled as a sandbox window to play with JavaFX and positioning during development
*/
package passwordfactory;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AboutScreen 
{
    public void show() 
    {
        Stage aboutStage = new Stage();
        aboutStage.initModality(Modality.APPLICATION_MODAL);
        aboutStage.setTitle("About Password Factory");

  // Labels for project information
        Label projectLabel = new Label("Password Factory");
        Label courseLabel = new Label("CS236 - Advanced OOP");
        Label collegeLabel = new Label("Columbia Basin College");

        // Created By section
        Label createdByLabel = new Label("Created By:");
        Label authorsLabel = new Label("Lizbeth Garcia-Lopez & Jordan Bassett");

        // System information (Java & JavaFX versions)
        Label javaVersion = new Label("Java Version: " + SystemInfo.javaVersion());
        Label javafxVersion = new Label("JavaFX Version: " + SystemInfo.javafxVersion());

        // Close button
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> aboutStage.close());

        // Layout setup
        VBox layout = new VBox(10, projectLabel, courseLabel, collegeLabel, 
                               createdByLabel, authorsLabel, 
                               javaVersion, javafxVersion, closeButton);
        layout.setStyle("-fx-padding: 20px; -fx-alignment: center;");

        // Set scene and display
        aboutStage.setScene(new Scene(layout, 400, 250));
        aboutStage.show();
    }
}