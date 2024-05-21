package view;

import controller.ApplicationController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import model.App;

public class SettingController {
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    public void initialize(){
        AppViewController.playMusic("src/main/media/Billie_Eilish_-_CHIHIRO_@BaseNaija.mp3");
        AppViewController.setIcon();
        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            handleEvent(newValue);
        });
    }
    private void handleEvent(String newChoice){
        if (newChoice.equals("easy")){
            App.setGameDifficulty(1);
            App.setMigTimeCoef(1);
        }
        else if (newChoice.equals("medium")){
            App.setGameDifficulty(2);
            App.setMigTimeCoef(0.75);
        }
        else if (newChoice.equals("hard")){
            App.setGameDifficulty(3);
            App.setMigTimeCoef(0.5);
        }
    }

    public void back(MouseEvent mouseEvent) {
        AppViewController.mainMenu.start(ApplicationController.getStage());
    }

    public void muteGame(MouseEvent mouseEvent) {
        App.setMuted(true);
        AppViewController.showAlert("Game is now muted", "Mute" ,Alert.AlertType.INFORMATION, "/Images/backgrounds/setting.png", true);
    }

    public void grayTheGame(MouseEvent mouseEvent) {
        App.setGrayScale(true);
        AppViewController.showAlert("Game is now in grayscale", "Gray Scale" ,Alert.AlertType.INFORMATION, "/Images/backgrounds/setting.png", true);
    }
}
