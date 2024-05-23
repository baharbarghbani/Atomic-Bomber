package view;

import controller.ApplicationController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import static java.util.Objects.requireNonNull;

public class EndGameMenu extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ApplicationController.setStage(primaryStage);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(requireNonNull(getClass().getResource("/FXML/EndGameFxml.fxml")));
            Pane root = fxmlLoader.load();
            Scene currentScene = new Scene(root);
            root.getStyleClass().add("end-game-menu");
            primaryStage.setResizable(false);
            currentScene.getStylesheets().add(getClass().getResource("/CSS/style.css").toExternalForm());
            primaryStage.setScene(currentScene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
