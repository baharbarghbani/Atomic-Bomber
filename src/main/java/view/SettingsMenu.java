package view;

import controller.ApplicationController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class SettingsMenu extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(javafx.stage.Stage primaryStage) throws Exception {
        ApplicationController.setStage(primaryStage);
        try {
            FXMLLoader fxmlLoader = new javafx.fxml.FXMLLoader();
            Pane root = fxmlLoader.load(java.util.Objects.requireNonNull(getClass().getResource("/FXML/SettingFxml.fxml")));

            // Set the icon for the stage
            primaryStage.setTitle("Setting");
            // Create custom title bar layout
            // Set the scene
            Scene currentScene = new Scene(root);
            root.getStyleClass().add("setting-menu");
            currentScene.getStylesheets().add(getClass().getResource("/CSS/style.css").toExternalForm());
//            stage.setTitle("Login Menu");
            primaryStage.setScene(currentScene);

            // Show the stage
            primaryStage.show();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
         primaryStage.setTitle("Setting");
         primaryStage.show();
    }
}
