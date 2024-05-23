package view;

import controller.ApplicationController;
import controller.ProfileMenuController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.App;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;


public class AppViewController {
    public static GameLauncherController gameLauncherController = new GameLauncherController();
    static ProfileMenuController profileMenuController = new ProfileMenuController();
    static ChangeUsername changeUsername = new ChangeUsername();
    static AppViewController appController = new AppViewController();
    static LoginMenu loginMenu = new LoginMenu();
    static SettingsMenu settingsMenu = new SettingsMenu();
    static MainMenu mainMenu = new MainMenu();
    static ScoreBoard scoreBoard = new ScoreBoard();
    static Media media;
    static MediaPlayer mediaPlayer;
    static String musicPath;

    public static void playMusic(String path) {
        if (media != null) {
            mediaPlayer.stop();
        }
        musicPath = path;
        media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        if (!App.isMuted()) mediaPlayer.play();
    }

    public static void pauseMusic() {
        if (mediaPlayer != null) mediaPlayer.pause();
    }

    @FXML
    public static void showAlert(String errorMessage, String title, Alert.AlertType alertType, String imagePath, boolean wait) {
        Platform.runLater(() -> {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(errorMessage);
            ImageView imageView = new ImageView(new Image(Objects.requireNonNull(ApplicationController.class.getResourceAsStream(imagePath))));
            imageView.setFitWidth(300);
            imageView.setFitHeight(200);
            StackPane dialogPane = new StackPane();
            dialogPane.getChildren().add(imageView);
            alert.getDialogPane().setContent(dialogPane);
            alert.getDialogPane().setStyle("-fx-background-color: transparent;");
            alert.getDialogPane().setBackground(new Background(new BackgroundImage(imageView.getImage(), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
            if (wait) alert.showAndWait();
            else alert.show();
        });
    }

    public static void setIcon() {
        Image icon = new Image(Objects.requireNonNull(AppViewController.class.getResourceAsStream("/Images/icon.png")));
        ImageView iconView = new ImageView(icon);
        iconView.setFitHeight(16);
        iconView.setFitWidth(16);
        ApplicationController.getStage().getIcons().add(iconView.getImage());
    }

    public Image imageInitialize() throws FileNotFoundException {
        if (App.getLoggedInUser() == null) {
            return null;
        }
        String path = App.getLoggedInUser().getAvatarPath();
        if (path.contains("/Images/avatars"))
            return new Image(Objects.requireNonNull(Objects.requireNonNull(getClass().getResourceAsStream(path))));
        else return new Image(new FileInputStream(path));
    }


}
