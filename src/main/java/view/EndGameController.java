package view;

import controller.ApplicationController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.Game;

public class EndGameController {
    private static Label gameResultLabel;
    private static Label waveLabel;
    private static Label accuracyLabel;
    private static Label killLabel;
    @FXML
    public Label gameResult;
    @FXML
    public Label wave;
    @FXML
    public Label accuracy;
    @FXML
    public Label kill;

    @FXML
    public void initialize() {
        gameResultLabel = gameResult;
        gameResultLabel.setText("You " + ApplicationController.getGameResult());
        waveLabel = wave;
        waveLabel.setText("Wave: " + Game.getInstance().getWaveNumber());
        accuracyLabel = accuracy;
        accuracyLabel.setText("Accuracy: " + ApplicationController.getAccuracy() + "%");
        killLabel = kill;
        killLabel.setText("Kill: " + ApplicationController.getKill());

    }

    @FXML
    public void back(MouseEvent mouseEvent) {
        AppViewController.mainMenu.start(ApplicationController.getStage());
    }

}
