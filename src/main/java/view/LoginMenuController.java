package view;

import controller.ApplicationController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.App;
import model.Result;
import model.User;

import java.util.ArrayList;

public class LoginMenuController {
    static MainMenu mainMenu = new MainMenu();
    private final controller.LoginMenuController controller = new controller.LoginMenuController();
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    @FXML
    public void initialize() {
        AppViewController.setIcon();
        AppViewController.playMusic("src/main/media/Billie_Eilish_-_CHIHIRO_@BaseNaija.mp3");
        if (ApplicationController.loadUsers() != null) {
            ArrayList<User> loadedUsers = new ArrayList<>(ApplicationController.loadUsers());
            App.loadUsers(loadedUsers);
        }
    }

    @FXML
    public void login() {
        Result loginResult = controller.login(username.getText(), password.getText());
        if (!loginResult.isSuccess()) {
            AppViewController.showAlert(loginResult.getMessage(), "Login Failed!", Alert.AlertType.WARNING, "/Images/backgrounds/baharBG3.png", true);
        } else {
            try {
                mainMenu.start(ApplicationController.getStage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void signUp() {
        Result result = controller.signUp(username.getText(), password.getText());
        if (!result.isSuccess()) {
            AppViewController.showAlert(result.getMessage(), "Sign up Failed!", Alert.AlertType.WARNING, "/Images/backgrounds/baharBG3.png", true);
        } else {
            try {
                mainMenu.start(ApplicationController.getStage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void recoverPassword() {
        Result result = controller.recoverPassword(username.getText());
        if (!result.isSuccess()) {
            AppViewController.showAlert(result.getMessage(), "Recover Password Failed!", Alert.AlertType.WARNING, "/Images/backgrounds/baharBG3.png", true);
        } else
            AppViewController.showAlert(result.getMessage(), "Recover Password", Alert.AlertType.INFORMATION, "/Images/backgrounds/baharBG3.png", true);
    }

    @FXML
    public void registerAsGuest() {
        Result result = controller.startGameAsGuest();
        AppViewController.showAlert(result.getMessage(), "Guest Mode", Alert.AlertType.INFORMATION, "/Images/backgrounds/baharBG3.png", true);
        try {
            mainMenu.start(ApplicationController.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
