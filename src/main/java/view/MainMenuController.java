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
    static ProfileMenu profileMenu = new ProfileMenu();
    static AppViewController menuController = new AppViewController();
    @FXML
    private ImageView imageView;
    @FXML
    private Label username;

    @FXML
    public void openProfileMenu() throws Exception {
        profileMenu.start(ApplicationController.getStage());
    }

    @FXML
    public void initialize() throws FileNotFoundException {
        AppViewController.setIcon();
        Image image = menuController.imageInitialize();
        imageView.setImage(image);
        username.setStyle("-fx-alignment: center");
        username.setText(App.getLoggedInUser().getUsername());
    }

    @FXML
    public void startNewGame() throws Exception {
        new GameLauncher().start(ApplicationController.getStage());
    }

    @FXML
    public void openSettings() {
        try {
            AppViewController.settingsMenu.start(ApplicationController.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void continueGame(MouseEvent mouseEvent) {
    }

    public void openScoreBoard(MouseEvent mouseEvent) {
        try {
            AppViewController.scoreBoard.start(ApplicationController.getStage());
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @FXML
    public void exit() {
        System.exit(0);
    }
}
