package view;

import controller.ApplicationController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

import static java.util.Objects.requireNonNull;

public class ProfileMenu extends Application {
    static Scene currentScene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ApplicationController.setStage(stage);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Pane root = FXMLLoader.load(requireNonNull(getClass().getResource("/FXML/ProfileMenuFxml.fxml")));
            stage.setTitle("Profile Menu");
            currentScene = new Scene(root);
            root.getStyleClass().add("profile-menu");
            currentScene.getStylesheets().add(getClass().getResource("/CSS/style.css").toExternalForm());
            stage.setScene(currentScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
