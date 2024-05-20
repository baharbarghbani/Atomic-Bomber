package view;

import controller.ApplicationController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import model.App;
import model.Game;

public class SettingController {
    @FXML
    private ChoiceBox<String> choiceBox;
//    public void easy(MouseEvent mouseEvent) {
//        App.setGameDifficulty(1);
//        App.setGameDifficulty(1);
//    }
//
//    public void medium(MouseEvent mouseEvent) {
//        App.setGameDifficulty(2);
//        App.setMigTimeCoef(0.75);
//    }
//
//    public void hard(MouseEvent mouseEvent) {
//        App.setGameDifficulty(3);
//        App.setMigTimeCoef(0.5);
//    }
    @FXML
    public void initialize(){
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
        AppController.mainMenu.start(ApplicationController.getStage());
    }

    public void muteGame(MouseEvent mouseEvent) {
    }

    public void grayTheGame(MouseEvent mouseEvent) {
        App.setGrayScale(true);
        AppController.showAlert("Game is now in grayscale", "Gray Scale" ,Alert.AlertType.INFORMATION, "/Images/backgrounds/setting.png", true);
    }
}
