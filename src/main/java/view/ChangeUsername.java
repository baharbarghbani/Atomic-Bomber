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
    @FXML
    public TextField username;
    @FXML
    public PasswordField password;
    static Scene currentScene;

    @Override
    public void start(Stage stage) throws IOException {
        ApplicationController.setStage(stage);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Pane root = fxmlLoader.load(requireNonNull(getClass().getResource("/FXML/ChangeUsername.fxml")));

            // Set the icon for the stage
            stage.setTitle("Change Username");
            // Create custom title bar layout
            // Set the scene
            currentScene = new Scene(root);
//            stage.setTitle("Login Menu");
            stage.setScene(currentScene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void initialize() throws FileNotFoundException {
        AppController.appController.imageInitialize();
        AppController.appController.setIcon();
    }
    public void changeUsername() {
//        ProfileMenuController profileMenuController = new ProfileMenuController();
        ProfileMenuController profileMenuController = AppController.profileMenuController;
        Result result = profileMenuController.changeInfo(username.getText(), password.getText());
        if (!result.isSuccess()){
            AppController.showAlert(result.getMessage(), "Changing username failed!", Alert.AlertType.WARNING, "/Images/backgrounds/background1.png");
        }
        else {
            AppController.showAlert(result.getMessage(), "Changed username and password successfully!", Alert.AlertType.INFORMATION, "/Images/backgrounds/background1.png");
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
