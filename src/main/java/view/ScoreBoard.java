package view;

import controller.ApplicationController;
import controller.GameController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.App;
import model.GameScore;
import model.User;

import java.util.ArrayList;
import java.util.Objects;

public class ScoreBoard extends Application {
    @FXML
    private ToggleGroup group;
    @FXML
    private TableView table;
    @FXML
    private TableColumn usernameColumn;
    @FXML
    private TableColumn killsColumn;
    @FXML
    private TableColumn difficultyColumn;
    @FXML
    private TableColumn accuracyColumn;
    @FXML
    private TableColumn lastWaveColumn;

    @FXML
    public void initialize() {
        AppViewController.setIcon();
        ArrayList<User> loadedUsers = new ArrayList<>(ApplicationController.loadUsers());
        App.loadUsers(loadedUsers);
        GameScore.refresh(0);
        refreshTable();
//        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue != null) {
//                RadioButton selectedRadioButton = (RadioButton) newValue;
//                String button = selectedRadioButton.getText();
//                int n = 0;
//                if (button.equals("Difficulty")) n = 1;
//                else if (button.equals("Accuracy")) n = 2;
//                GameScore.refresh(n);
//                refreshTable();
//            }
//        });
    }

    private void refreshTable() {
//        TableColumn<GameScore, String> firstNameColumn = new TableColumn<>("Username");
//        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
//
//        TableColumn<GameScore, String> killsColumn = new TableColumn<>("Kills");
//        killsColumn.setCellValueFactory(new PropertyValueFactory<>("kills"));
//
//        TableColumn<GameScore, String> hardnessColumn = new TableColumn<>("Difficulty");
//        hardnessColumn.setCellValueFactory(new PropertyValueFactory<>("hardness"));
//
//        TableColumn<GameScore, Integer> accuracyColumn = new TableColumn<>("Accuracy%");
//        accuracyColumn.setCellValueFactory(new PropertyValueFactory<>("accuracy"));
//
//        TableColumn<GameScore, Integer> lastWaveColumn = new TableColumn<>("Last Wave");
//        lastWaveColumn.setCellValueFactory(new PropertyValueFactory<>("lastWave"));

//        table.getColumns().clear();
//        table.getColumns().add(usernameColumn);
//        table.getColumns().add(killsColumn);
//        table.getColumns().add(difficultyColumn);
//        table.getColumns().add(accuracyColumn);
//        table.getColumns().add(lastWaveColumn);
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        killsColumn.setCellValueFactory(new PropertyValueFactory<>("kills"));
        difficultyColumn.setCellValueFactory(new PropertyValueFactory<>("hardness"));
        accuracyColumn.setCellValueFactory(new PropertyValueFactory<>("accuracy"));
        lastWaveColumn.setCellValueFactory(new PropertyValueFactory<>("lastWave"));
        ObservableList<GameScore> data = FXCollections.observableArrayList(GameScore.getAllGameScores());
        table.setRowFactory(new Callback<TableView<GameScore>, TableRow<GameScore>>() {
            @Override
            public TableRow<GameScore> call(TableView<GameScore> tableView) {
                return new TableRow<GameScore>() {
                    @Override
                    protected void updateItem(GameScore score, boolean empty) {
                        super.updateItem(score, empty);
                        if (getIndex() < 3 && !empty) {
                            setStyle("-fx-background-color: rgb(105, 208, 172);");
                        }
                    }
                };
            }
        });
        table.setItems(data);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ApplicationController.setStage(primaryStage);
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        Pane pane = FXMLLoader.load(Objects.requireNonNull(ScoreBoard.class.getResource("/FXML/ScoreBoardFxml.fxml")));
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        AppViewController.mainMenu.start(ApplicationController.getStage());
    }
}
