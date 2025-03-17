/* Password Factory Project
 CS236 - Advanced OOP (Java 2)
 Class: AboutScreen.java
 Author(s): Lizbeth Garcia-Lopez & Jordan Bassett
 Last Updated: March 16, 2025
 
 Description:
    This class creates a simple "About" screen that provides information about the game
    and displays system details, such as Java and JavaFX versions.
 
Features:
- Displays game title, version, and credits.
- Uses SystemInfo.java to retrieve Java and JavaFX version details.
- Provides a "Close" button to dismiss the window.
 
Dependencies:
- Called from: App.java (when the "About" button is clicked).
- Uses: SystemInfo.java (provided by Netbeans) */
package passwordfactory;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

//Displays the About window with project and system information
public class AboutScreen
{
    public void show()
    {
        //create a new Stage for the About window
        Stage aboutStage = new Stage();

        //Block interaction with other windows until closed
        //entire application is locked until this stage closes
        aboutStage.initModality(Modality.APPLICATION_MODAL);
        aboutStage.setTitle("About Password Factory");

        //Labels for project info
        Label projectLabel = new Label("Password Factory");
        Label courseLabel = new Label("CS236 - Advanced OOP");
        Label collegeLabel = new Label("Columbia Basin College");

        //created By section
        Label createdByLabel = new Label("Created By:");
        Label authorsLabel = new Label("Lizbeth Garcia-Lopez & Jordan Bassett");

        //System information (Java & JavaFX versions)
        Label javaVersion = new Label("Java Version: " + SystemInfo.javaVersion());
        Label javafxVersion = new Label("JavaFX Version: " + SystemInfo.javafxVersion());

        //Close button to close the About window
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> aboutStage.close());

        //Layout setup for the About window
        VBox layout = new VBox(10,
            projectLabel,
            courseLabel,
            collegeLabel,
            createdByLabel,
            authorsLabel,
            javaVersion,
            javafxVersion,
            closeButton
        );

        layout.setStyle("-fx-padding: 20px; -fx-alignment: center;");

        // Set the scene and display the About window
        aboutStage.setScene(new Scene(layout, 400, 250));
        aboutStage.show();
    }
}