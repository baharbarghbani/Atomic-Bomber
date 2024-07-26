package view;

import controller.ApplicationController;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import static view.MainMenuController.menuController;

public class ProfileMenuController {
    @FXML
    private ImageView imageView;

    @FXML
    public void initialize() throws Exception {
        AppViewController.setIcon();
        Image image = menuController.imageInitialize();
        imageView.setImage(image);
    }

    @FXML
    public void changeUsername() {
        try {
            AppViewController.changeUsername.start(ApplicationController.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteAccount() {
        AppViewController.profileMenuController.deleteAccount();

    }

    @FXML
    public void logout() {
        AppViewController.profileMenuController.logout();

    }

    @FXML
    public void openAvatarMenu() {
        AppViewController.profileMenuController.goToAvatarMenu();
    }

    public void back(MouseEvent mouseEvent) {
        try {
            new MainMenu().start(ApplicationController.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
