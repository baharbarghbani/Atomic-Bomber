package view;

import controller.ApplicationController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class GameLauncher extends Application {
    protected static GameLauncher instance;
    public Pane root;
    public Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    public static GameLauncher getInstance() {
        return instance;
    }

    public static void setInstance(GameLauncher gameLauncher) {
        instance = gameLauncher;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        ApplicationController.setStage(primaryStage);
        setInstance(this);
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/FXML/GameFxml.fxml")));
        root = fxmlLoader.load();
        root.getStyleClass().add("game");
        scene = new Scene(root);
        AppViewController.gameLauncherController.setRoot(root);
        AppViewController.gameLauncherController.createGameLauncher(scene);
        AppViewController.gameLauncherController.addLives();
        primaryStage.setTitle("Atomic Bomber");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        AppViewController.gameLauncherController.checkGrayScale(primaryStage);
        primaryStage.show();
    }
}