package view;
import animations.BombAnimtaion;
import animations.PlaneAnimation;
import controller.ApplicationController;
import controller.GameController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.App;
import model.Game;
import model.Wave;
import model.components.Cluster;
import model.components.NuclearBomb;
import model.components.Plane;
import model.components.Rocket;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class GameLauncher extends Application {
    @FXML
    public Label username;
    @FXML
    public ImageView userAvatar;
    @FXML
    public ImageView pauseButton;
    @FXML
    public Label clusterBombNumber;
    @FXML
    public ImageView clusterBomb;
    @FXML
    protected ImageView rocket;
    @FXML
    public Label  rocketNumber;
    @FXML
    public Label nuclearBombNumber;
    @FXML
    protected ImageView nuclearBomb;
    @FXML
    public Label killNumber;
    @FXML
    public ImageView killNumberImage;
    @FXML
    public Label waveNumber;
    private  Game game;
    public static Label killNumberText;
    public static Label rocketNumberText;
    public static Label nuclearBombNumberText;
    public static Label waveNumberText;
    public static Label clusterBombNumberText;
    public static ImageView pauseButtonImage;
    private Plane plane;
    public Pane root;
    public Pane pauseMenu;
    public Pane endGameMenu;
    public Scene scene;
    protected static GameLauncher instance;

    public static int numberOfRockets = 0;
    public static int numberOfNuclearBombs = 0;

    public static void main(String[] args) {
        launch(args);
    }
    public static GameLauncher getInstance(){
        return instance;
    }
    public static void setInstance(GameLauncher gameLauncher){
        instance = gameLauncher;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        ApplicationController.setStage(primaryStage);
        setInstance(this);
        createGameLauncher();
        primaryStage.setTitle("Atomic Bomber");
        primaryStage.setScene(scene);
        primaryStage.show();
        plane.requestFocus();
    }
    public void createGameLauncher() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/GameFxml.fxml"));
        root = fxmlLoader.load();
        root.getStyleClass().add("game");
        scene = new Scene(root);
        createGame();
        creatPlane(root);
        game.setPlane(plane);
        Wave wave = GameController.createWave(game);
        scene.getStylesheets().add(getClass().getResource("/CSS/style.css").toExternalForm());
        root.getChildren().add(plane);
        plane.requestFocus();
        root.getChildren().addAll(wave.getTrees());
        root.getChildren().addAll(wave.getBuildings());
        root.getChildren().addAll(wave.getForts());
        root.getChildren().addAll(wave.getTanks());
        root.getChildren().addAll(wave.getTrucks());
        GameLauncherController.createTanksAnimation(wave.getTanks(), game, 3);
        GameLauncherController.createTrucksAnimation(wave.getTrucks(), game);
    }
    public void createPauseMenu(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/PauseMenuFxml.fxml"));
        try {
            pauseMenu = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pauseMenu.getStyleClass().add("pause-menu");
        root.getChildren().add(pauseMenu);


    }
    @FXML
    public void initialize() throws FileNotFoundException {
        AppController.appController.setIcon();
        rocket.setImage(AppController.gameLauncherController.getRocket());
        rocketNumber.setText("X" + App.getLoggedInUser().getRocketNumber());
        nuclearBomb.setImage(AppController.gameLauncherController.getNuclearBomb());
        nuclearBombNumber.setText("X" + App.getLoggedInUser().getNuclearBombNumber());
        killNumberImage.setImage(AppController.gameLauncherController.getKillNumber());
        killNumber.setText("X" + App.getLoggedInUser().getKill());
        killNumberText = killNumber;
        nuclearBombNumberText = nuclearBombNumber;
        rocketNumberText = rocketNumber;
        username.setText(App.getLoggedInUser().getUsername());
        userAvatar.setImage(AppController.appController.imageInitialize());
        waveNumber.setText("Wave 1");
        waveNumberText = waveNumber;
        pauseButton.setImage(new Image(requireNonNull(getClass().getResourceAsStream("/Images/pause.png"))));
        pauseButtonImage = pauseButton;
        clusterBomb.setImage(new Image(requireNonNull(getClass().getResourceAsStream("/Images/bombs/malware.png"))));
        clusterBombNumber.setText("X" + App.getLoggedInUser().getClusterBombNumber());
        clusterBombNumberText = clusterBombNumber;
    }
    public void createGame() {
        game = new Game(root);
        Game.setInstance(game);
    }

    private void creatPlane(Pane pane) {
        plane = new Plane(game, pane);
        plane.setFocusTraversable(true);
        plane.requestFocus();
        PlaneAnimation planeAnimation = new PlaneAnimation(game, pane, plane);
        planeAnimation.play();
        plane.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) {
                planeAnimation.setUp(true);
                plane.setDisable(false);
            }
            if (event.getCode() == KeyCode.DOWN) {
                planeAnimation.setDown(true);
                plane.setDisable(false);
            }
            if (event.getCode() == KeyCode.LEFT) {
                planeAnimation.setLeft(true);
                plane.setDisable(false);
            }
            if (event.getCode() == KeyCode.RIGHT) {
                planeAnimation.setRight(true);
                plane.setDisable(false);
            }
        });
        plane.setOnKeyReleased(event -> {
            plane.setFocusTraversable(true);
            plane.requestFocus();
            if (event.getCode() == KeyCode.RIGHT) {
                planeAnimation.setRight(false);
                plane.setDisable(false);
            }
            if (event.getCode() == KeyCode.LEFT) {
                planeAnimation.setLeft(false);
                plane.setDisable(false);
            }
            if (event.getCode() == KeyCode.UP) {
                planeAnimation.setUp(false);
                plane.setDisable(false);
            }
            if (event.getCode() == KeyCode.DOWN) {
                planeAnimation.setDown(false);
                plane.setDisable(false);
            }
            if (event.getCode() == KeyCode.SPACE) {
                GameController.increaseShootingCount();
                System.out.println(App.getLoggedInUser().getShootingCount());
                plane.setDisable(false);
                double angle = -plane.getAngle() + Math.PI/2;
                double y;
                double x = plane.getX() + plane.getWidth()/2;
                if (plane.getAngle() > Math.PI && plane.getAngle() < Math.PI * 2) {
                    y = plane.getY() + plane.getHeight() / 2;
                } else {
                    y = plane.getY() + plane.getHeight() / 3;
                }
                if (plane.flipped)
                    x -= 10;
                double vy = -planeAnimation.getSpeed() * Math.sin(plane.angle);
                double vx = planeAnimation.getSpeed() * Math.cos(plane.angle);

                Rocket rocket = new Rocket(x, y, angle, plane.flipped, vx, vy, root);
                root.getChildren().add(rocket);
                GameLauncherController.shootBombs(rocket);
                plane.requestFocus();
            }
            if (event.getCode() == KeyCode.R){
                if (App.getLoggedInUser().getNuclearBombNumber() == 0)
                    return;
                createNuclear(planeAnimation);

            }
            if (event.getCode() == KeyCode.C){
                if (App.getLoggedInUser().getClusterBombNumber() == 0)
                    return;
                createCluster(planeAnimation);
            }
            if (event.getCode() == KeyCode.P){
                if (!Game.getInstance().getWave().getAllObjects().isEmpty()){
                    GameLauncherController.jumpToNextWave();
                }
                GameController.goToNextWave();
            }
            if (event.getCode() == KeyCode.G){
                App.getLoggedInUser().addNuclearBomb();
                GameLauncherController.updateNuclearBombCount();
            }
            if (event.getCode() == KeyCode.T){
                Wave wave = Game.getInstance().getWave();
                GameController.createTanks(wave, game, root, 1);
                GameLauncherController.createTankCheatCode(wave.getTanks());
            }
            if (event.getCode() == KeyCode.CONTROL) {
                App.getLoggedInUser().addCluster();
                GameLauncherController.updateClusterBombCount();
            }
        });
    }

    public void createNuclear(PlaneAnimation planeAnimation) {
        double angle = -plane.getAngle() + Math.PI/2;
        double y;
        double x = plane.getX() + plane.getWidth()/2;
        if (plane.getAngle() > Math.PI && plane.getAngle() < Math.PI * 2) {
            y = plane.getY() + plane.getHeight() / 2;
        } else {
            y = plane.getY() + plane.getHeight() / 3;
        }
        if (plane.flipped)
            x -= 10;
        double vy = -planeAnimation.getSpeed() * Math.sin(plane.angle);
        double vx = planeAnimation.getSpeed() * Math.cos(plane.angle);
        NuclearBomb nuclearBomb = new NuclearBomb(x, y, angle, plane.flipped, vx, vy, root, 30, 30);
        plane.setDisable(false);
        root.getChildren().add(nuclearBomb);
        App.getLoggedInUser().reduceNuclearBomb();
        GameLauncherController.updateNuclearBombCount();
        GameLauncherController.shootBombs(nuclearBomb);
        plane.requestFocus();
    }
    public void createCluster(PlaneAnimation planeAnimation){
        plane.setDisable(false);
        BombAnimtaion.setClusterHasExploded(false);
        double angle = -plane.getAngle() + Math.PI/2;
        double y;
        double x = plane.getX() + plane.getWidth()/2;
        if (plane.getAngle() > Math.PI && plane.getAngle() < Math.PI * 2) {
            y = plane.getY() + plane.getHeight() / 2;
        } else {
            y = plane.getY() + plane.getHeight() / 3;
        }
        if (plane.flipped)
            x -= 10;
        double vy = -planeAnimation.getSpeed() * Math.sin(plane.angle);
        double vx = planeAnimation.getSpeed() * Math.cos(plane.angle);
        Cluster cluster = new Cluster(x, y, angle, plane.flipped, vx, vy, root, 20, 20);
        root.getChildren().add(cluster);
        GameLauncherController.shootBombs(cluster);
        App.getLoggedInUser().reduceClusterBomb();
        GameLauncherController.updateClusterBombCount();
        plane.requestFocus();
    }
    public static void endGame() throws Exception {
        EndGameMenu endGameMenu = new EndGameMenu();
        endGameMenu.start(ApplicationController.getStage());
    }


    public void pauseGame(MouseEvent mouseEvent) throws IOException {
        GameLauncherController.pauseGame();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/PauseMenuFxml.fxml"));
        Pane pane = fxmlLoader.load();
        root.getChildren().add(pane);
        ApplicationController.getStage().setScene(scene);
        ApplicationController.getStage().show();
    }

    public void exitWithSave(ActionEvent actionEvent) {
    }

    public void exitWithoutSave(ActionEvent actionEvent) {
    }

    public void pauseMusic(ActionEvent actionEvent) {
    }

    public void keyGuide(ActionEvent actionEvent) {
    }

    public void changeMusic(ActionEvent actionEvent) {
    }

    public void back(ActionEvent actionEvent) {

    }
}