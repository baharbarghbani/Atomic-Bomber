package view;

import controller.ApplicationController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.App;

import java.io.FileNotFoundException;



public class MainMenuController {
    @FXML
    public ImageView imageView;
    @FXML
    public Label username;
    static ProfileMenu profileMenu = new ProfileMenu();
    static AppController menuController = new AppController();
    @FXML
    public void openProfileMenu() throws Exception {
        profileMenu.start(ApplicationController.getStage());
    }
    @FXML
    public void initialize() throws FileNotFoundException {
        AppController.appController.setIcon();
        Image image = menuController.imageInitialize();
        imageView.setImage(image);
        username.setStyle("-fx-alignment: center");
        username.setText(App.getLoggedInUser().getUsername());
    }
    @FXML
    public void openScoreboardMenu() throws Exception {
        // ScoreboardMenu scoreboardMenu = new ScoreboardMenu();
        // scoreboardMenu.start(ApplicationController.getStage());
    }

    @FXML
    public void startNewGame() throws Exception {
        new GameLauncher().start(ApplicationController.getStage());
    }

    @FXML
    public void openSettings() {
        try {
            AppController.settingsMenu.start(ApplicationController.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void continueGame(MouseEvent mouseEvent) {
    }

    public void openScoreBoard(MouseEvent mouseEvent) {
    }

    @FXML
    public void exit() {
        System.exit(0);
    }
}
