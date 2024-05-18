package view;

import controller.ApplicationController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AvatarMenu extends Application {
    static Scene currentScene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        ApplicationController.setStage(stage);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/FXML/AvatarMenuFxml.fxml")));
            Pane root = fxmlLoader.load();

            // Set the icon for the stage
            stage.setTitle("Avatar Menu");
            // Create custom title bar layout
            // Set the scene
            currentScene = new Scene(root);
            root.getStyleClass().add("avatar-menu");
            currentScene.getStylesheets().add(getClass().getResource("/CSS/style.css").toExternalForm());
            stage.setScene(currentScene);
            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
