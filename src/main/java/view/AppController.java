package view;

import controller.ApplicationController;
import controller.ProfileMenuController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import model.App;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

public class AppController {
    static ProfileMenuController profileMenuController = new ProfileMenuController();
    static ChangeUsername changeUsername = new ChangeUsername();
    static GameLauncherController gameLauncherController = new GameLauncherController();
    static AppController appController = new AppController();
    static LoginMenu loginMenu = new LoginMenu();
    static SettingsMenu settingsMenu = new SettingsMenu();
    public Image imageInitialize() throws FileNotFoundException {
        if (App.getLoggedInUser() == null) {
            return null;
        }
        String path = App.getLoggedInUser().getAvatarPath();
        if (path.contains("/Images/avatars"))
            return new Image(Objects.requireNonNull(Objects.requireNonNull(getClass().getResourceAsStream(path))));
        else
            return new Image(new FileInputStream(path));
    }
    @FXML
    public static void showAlert(String errorMessage, String title, Alert.AlertType alertType, String imagePath, boolean wait){
        Platform.runLater(() -> {
            // UI-related code here
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(errorMessage);
            ImageView imageView = new ImageView(new Image(Objects.requireNonNull(ApplicationController.class.getResourceAsStream(imagePath))));
            imageView.setFitWidth(300);
            imageView.setFitHeight(200);
            StackPane dialogPane = new StackPane();
            dialogPane.getChildren().add(imageView);
            alert.getDialogPane().setContent(dialogPane);

            // Apply the background to the header area
            alert.getDialogPane().setStyle("-fx-background-color: transparent;");
            // Set the background of the alert dialog
            alert.getDialogPane().setBackground(new Background(new BackgroundImage(
                    imageView.getImage(),
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    BackgroundSize.DEFAULT)));
            if (wait)
                alert.showAndWait();
            else
                alert.show();
        });
    }
    public void setIcon(){
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/icon.png")));
        ImageView iconView = new ImageView(icon);
        iconView.setFitHeight(16);
        iconView.setFitWidth(16);
        ApplicationController.getStage().getIcons().add(iconView.getImage());
    }


}
