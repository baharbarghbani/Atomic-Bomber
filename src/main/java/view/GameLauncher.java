package view;
import animations.PlaneAnimation;
import controller.ApplicationController;
import controller.GameController;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.App;
import model.Game;
import model.Wave;
import model.components.NuclearBomb;
import model.components.Plane;
import model.components.Rocket;

import java.io.IOException;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class GameLauncher extends Application {
    @FXML
    public Label username;
    @FXML
    public ImageView userAvatar;
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
    private Plane plane;
    public Pane root;
    static GameLauncherController gameLauncherController = new GameLauncherController();
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/GameFxml.fxml"));
        root = fxmlLoader.load();
        root.getStyleClass().add("game");
        Scene scene = new Scene(root);
        createGame();
        creatPlane(root);
        game.setPlane(plane);
        Wave wave = GameController.createWave(game);
        scene.getStylesheets().add(getClass().getResource("/CSS/style.css").toExternalForm());
        root.getChildren().add(plane);
        root.getChildren().addAll(wave.getTrees());
        root.getChildren().addAll(wave.getBuildings());
        root.getChildren().addAll(wave.getForts());
        root.getChildren().addAll(wave.getTanks());
        root.getChildren().addAll(wave.getTrucks());
        primaryStage.setTitle("Atomic Bomber");
        gameLauncherController.createTanks(wave.getTanks(), game);
        gameLauncherController.createTrucks(wave.getTrucks(), game);
        // Create the root pane
        // Show the scene
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    @FXML
    public void initialize(){
        ApplicationController applicationController = new ApplicationController();
        applicationController.setIcon();
        rocket.setImage(gameLauncherController.getRocket());
        rocketNumber.setText("X" + App.getLoggedInUser().getRocketNumber());
        nuclearBomb.setImage(gameLauncherController.getNuclearBomb());
        nuclearBombNumber.setText("X" + App.getLoggedInUser().getNuclearBombNumber());
        killNumberImage.setImage(gameLauncherController.getKillNumber());
        killNumber.setText("X" + App.getLoggedInUser().getKill());
        killNumberText = killNumber;
        nuclearBombNumberText = nuclearBombNumber;
        rocketNumberText = rocketNumber;
        username.setText(App.getLoggedInUser().getUsername());
        userAvatar.setImage(new Image(Objects.requireNonNull(Objects.requireNonNull(getClass().getResourceAsStream(App.getLoggedInUser().getAvatarPath())))));
        waveNumber.setText("Wave 1");
        waveNumberText = waveNumber;
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
            }
            if (event.getCode() == KeyCode.DOWN) {
                planeAnimation.setDown(true);
            }
            if (event.getCode() == KeyCode.LEFT) {
                planeAnimation.setLeft(true);
            }
            if (event.getCode() == KeyCode.RIGHT) {
                planeAnimation.setRight(true);
            }
        });
        plane.setOnKeyReleased(event -> {
            plane.requestFocus();
            if (event.getCode() == KeyCode.RIGHT) {
                planeAnimation.setRight(false);
            }
            if (event.getCode() == KeyCode.LEFT) {
                planeAnimation.setLeft(false);
            }
            if (event.getCode() == KeyCode.UP) {
                planeAnimation.setUp(false);
            }
            if (event.getCode() == KeyCode.DOWN) {
                planeAnimation.setDown(false);
            }
            if (event.getCode() == KeyCode.SPACE) {
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
            }
            if (event.getCode() == KeyCode.R){
                if (App.getLoggedInUser().getNuclearBombNumber() == 0)
                    return;
                NuclearBomb nuclearBomb = getNuclearBomb(planeAnimation);
                root.getChildren().add(nuclearBomb);
                App.getLoggedInUser().reduceNuclearBomb();
                GameLauncherController.updateNuclearBombCount();
                GameLauncherController.shootBombs(nuclearBomb);
            }
        });
    }

    private NuclearBomb getNuclearBomb(PlaneAnimation planeAnimation) {
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
        return nuclearBomb;
    }

    public static void endGame() throws Exception {
        EndGameMenu endGameMenu = new EndGameMenu();
        endGameMenu.start(ApplicationController.getStage());
    }

    public static void setKillNumberText(int kill){
        killNumberText.setText("X" + kill);
    }
    public static void setNuclearBombNumberText(int number){
        nuclearBombNumberText.setText("X" + number);
    }
    public void pause(){

    }


}