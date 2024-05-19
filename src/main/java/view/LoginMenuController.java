package view;

import controller.ApplicationController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.App;
import model.Result;
import model.User;
import view.MainMenu;

import java.util.ArrayList;

public class LoginMenuController {
    private controller.LoginMenuController controller = new controller.LoginMenuController();
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    static MainMenu mainMenu = new MainMenu();

    @FXML
    public void initialize() {
        AppController.appController.setIcon();
        if (ApplicationController.loadUsers() != null) {
            ArrayList<User> loadedUsers = new ArrayList<>(ApplicationController.loadUsers());
            App.loadUsers(loadedUsers);
        }
    }

    @FXML
    public void login() {
        Result loginResult = controller.login(username.getText(), password.getText());
        if (!loginResult.isSuccess()) {
            AppController.showAlert(loginResult.getMessage(), "Login Failed!", Alert.AlertType.WARNING, "/Images/backgrounds/baharBG3.png");
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
            AppController.showAlert(result.getMessage(), "Sign up Failed!", Alert.AlertType.WARNING, "/Images/backgrounds/baharBG3.png");
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
            AppController.showAlert(result.getMessage(), "Recover Password Failed!", Alert.AlertType.WARNING, "/Images/backgrounds/baharBG3.png");
        } else
            AppController.showAlert(result.getMessage(), "Recover Password", Alert.AlertType.INFORMATION, "/Images/backgrounds/baharBG3.png");
    }

    @FXML
    public void registerAsGuest() {
        Result result = controller.startGameAsGuest();
        AppController.showAlert(result.getMessage(), "Guest Mode", Alert.AlertType.INFORMATION, "/Images/backgrounds/baharBG3.png");
        try {
            mainMenu.start(ApplicationController.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
