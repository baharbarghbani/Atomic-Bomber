package view;

import controller.ApplicationController;
import controller.ProfileMenuController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Result;

import java.io.FileNotFoundException;
import java.io.IOException;

import static java.util.Objects.requireNonNull;

public class ChangeUsername extends Application {
    static Scene currentScene;
    @FXML
    public TextField username;
    @FXML
    public PasswordField password;

    @Override
    public void start(Stage stage) throws IOException {
        ApplicationController.setStage(stage);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Pane root = FXMLLoader.load(requireNonNull(getClass().getResource("/FXML/ChangeUsername.fxml")));
            currentScene = new Scene(root);
            stage.setScene(currentScene);
            stage.setTitle("Change Username");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() throws FileNotFoundException {
        AppViewController.appController.imageInitialize();
        AppViewController.setIcon();
    }

    public void changeUsername() {
        ProfileMenuController profileMenuController = AppViewController.profileMenuController;
        Result result = profileMenuController.changeInfo(username.getText(), password.getText());
        if (!result.isSuccess()) {
            AppViewController.showAlert(result.getMessage(), "Changing username failed!", Alert.AlertType.WARNING, "/Images/backgrounds/background1.png", true);
        } else {
            AppViewController.showAlert(result.getMessage(), "Changed username and password successfully!", Alert.AlertType.INFORMATION, "/Images/backgrounds/background1.png", true);
            try {
                new ProfileMenu().start(ApplicationController.getStage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void backToProfile(ActionEvent actionEvent) {
        try {
            new ProfileMenu().start(ApplicationController.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
