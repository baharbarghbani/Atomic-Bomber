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
            Pane root = FXMLLoader.load(java.util.Objects.requireNonNull(getClass().getResource("/FXML/SettingFxml.fxml")));
            primaryStage.setTitle("Setting");
            Scene currentScene = new Scene(root);
            root.getStyleClass().add("setting-menu");
            currentScene.getStylesheets().add(getClass().getResource("/CSS/style.css").toExternalForm());
            primaryStage.setScene(currentScene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("Setting");
        primaryStage.show();
    }
}
