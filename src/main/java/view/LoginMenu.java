package view;

import controller.ApplicationController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.GameScore;

import java.io.IOException;

import static java.util.Objects.requireNonNull;

public class LoginMenu extends Application {
    static Scene currentScene;

    public static void main(String[] args) {
        ApplicationController.loadUsers();
        System.out.println(GameScore.getAllGameScores().size());
        try {
            launch(args);
        } catch (Exception e) {
            ApplicationController.getStage().show();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        ApplicationController.setStage(stage);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(requireNonNull(getClass().getResource("/FXML/LoginMenuFxml.fxml")));
            Pane root = fxmlLoader.load();
            stage.setTitle("Login Menu");
            currentScene = new Scene(root);
            root.getStyleClass().add("login-menu");
            currentScene.getStylesheets().add(getClass().getResource("/CSS/style.css").toExternalForm());
            stage.setScene(currentScene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
