package passwordfactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Title Label
        Label titleLabel = new Label("Password Factory");

        // Buttons
        Button playGameButton = new Button("Play Game");
        Button passwordLabButton = new Button("Password Lab");
        Button exitButton = new Button("Exit");

        // Button Actions (Temporary)
        playGameButton.setOnAction(e -> System.out.println("Play Game clicked"));
        passwordLabButton.setOnAction(e -> System.out.println("Password Lab clicked"));
        exitButton.setOnAction(e -> {
            System.out.println("Exiting game...");
            primaryStage.close();
        });

        // Layout
        VBox root = new VBox(15, titleLabel, playGameButton, passwordLabButton, exitButton);
        root.setStyle("-fx-padding: 20px; -fx-alignment: center;");

        // Scene and Stage Setup
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Password Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

